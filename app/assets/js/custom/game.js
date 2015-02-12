/* Section AJAX */

function getState() {

    var url = "/game/status";
    $.ajax({
        url: url,
        success:setState,
        cache: false
    });
}

function getCard() {

    var url = "/game/card";
    $.ajax({
        url: url,
        success:setCard,
        cache: false
    });
}

function getCompetitorsCard() {
    var url = '/game/competitorcard';
    $.post(url, setCompetitorCard);
}

function chooseCategory(category) {

    var url = "/game/play";
    $.post(url, {cid: category});
   
    disableCategorySelection();
    setCategoryHighlightChoosen(category);
    setTimeout(update, 500);
 }

function commitCard() {

    var url = "/game/commitcard";
    $.post(url);
    setTimeout(update, 500);
}

function commitRound() {
    var url = "/game/commitround";
    $.post(url);
    setTimeout(update, 500);
}

function abortGame() {
    var url = "/game/abort";
    $.post(url);
}

/* Section Logic */
function update() {
    getState();
}

function setState(state){

    $('.game-info-cards-player').text(state.CardCountPlayer);
    $('.game-info-cards-competitor').text(state.CardCountCompetitor);
    //noinspection JSJQueryEfficiency
    $('#info-box').find('.round').text(state.Round);
    //noinspection JSJQueryEfficiency
    $('#info-box').find('.message').text(state.Message);
    
    if(state.RoundState != "OUTSTANDING"){
        clearCategoryHighlight();
        setCategoryHighlight(state.ChoosenCategory, state.RoundState);
    }    

    switch (state.State) {
        case "WaitForYourChoice":
            setStateChoice();
            break;
        case "WaitForCommitCard":
            setStateCommitCard();
            break;
        case "WaitForOtherPlayer":
            setStateWait();
            break;
        case "WaitForCommitRound":
            setStateCommit();
            break;
        case "Aborted":
        case "Won":
        case "Lost":
            setStateEndGame();
            break;
    }
}

function setStateChoice() {

    clearCategoryHighlight();
    setCompetitorsCardVisibilityTo(false);
    setCommitRoundButtonVisibilityTo(false);
    setCommitCardButtonVisibilityTo(false);

    getCard();
    enableCategorySelection();
}

function setStateCommitCard() {

    clearCategoryHighlight();
    setCompetitorsCardVisibilityTo(false);
    setCommitRoundButtonVisibilityTo(false);
    setCommitCardButtonVisibilityTo(true);

    getCard();
}

function setStateWait() {

    setCommitCardButtonVisibilityTo(false);
    setCommitRoundButtonVisibilityTo(false);
    setTimeout(update, 1000);
}

function setStateCommit() {

    getCompetitorsCard();
    setCompetitorsCardVisibilityTo(true);
    setCommitRoundButtonVisibilityTo(true);
    setCommitCardButtonVisibilityTo(false);
}

function setStateEndGame(){

    setCommitCardButtonVisibilityTo(false);
    setCommitRoundButtonVisibilityTo(false);
    $("#cards").hide();
    $('#game-result').show();
    $(window).off('beforeunload', preventNavigation);
}

function setCard(card) {
    
    var elem = $('#card-player');
    
    elem.find('.card-title').text(card.name);
    elem.find('img').attr('src', card.image).load(function () {
        $('.card-back').height($('#card-player').height());
    });

    for (cid = 0; cid < card.categories.length; cid++) {
        var cat = card.categories[cid];
        var selector = ".card-category-" + cid + '-value';
        elem.find(selector).text(cat.value);
    }
}

function setCompetitorCard(card) {

    var competitor = $('#card-competitor');
    // Set title
    competitor.find('.card-title').text(card.name);

    // Set image
    competitor.find('img').attr('src', card.image);

    // Set Categories
    for (var cid = 0; cid < card.categories.length; cid++) {

        var cat = card.categories[cid];
        var selector = ' .card-category-' + cid;

        // Set Value
        competitor.find(selector + '-value').text(cat.value);
    }
}

function setCategoryHighlight(category, roundstate) {

    var selector = '.card-category-' + category;
    var player = $('#card-player').find(selector);
    var competitor = $('#card-competitor').find(selector);
    
    if (roundstate == "WON") {
        player.addClass('list-group-item-success');
        competitor.addClass('list-group-item-danger');
    }
    else if(roundstate == "LOST"){
        player.addClass('list-group-item-danger');
        competitor.addClass('list-group-item-success');
    }
    else if(roundstate == "DRAWN"){
        player.addClass('list-group-item-warning');
        competitor.addClass('list-group-item-warning');
    }
}

function setCategoryHighlightChoosen(category){
    var selector = '.card-category-'+category;
    var player = $('#card-player').find(selector);
    
    player.addClass('list-group-item-info');   
}

function clearCategoryHighlight(){
    
    $('.card-category').removeClass('list-group-item-danger list-group-item-success list-group-item-warning list-group-item-info');
    
}

function setCompetitorsCardVisibilityTo(val) {

    var front = $('.card-front');
    var back = $('.card-back');

    if (val) {
        back.hide();
        front.show();
    }
    else {
        front.hide();
        back.height($('#card-player').height()).show();
    }
}

function setCommitRoundButtonVisibilityTo(val) {

    var btn = $('#info-button-commit-round');
    if (val) {

        btn.click(commitRound);
        btn.show();

    } else {
        btn.hide();
        btn.unbind("click");
    }
}

function setCommitCardButtonVisibilityTo(val) {

    var btn = $('#info-button-commit-card');
    if (val) {

        btn.click(commitCard);
        btn.show();

    } else {
        btn.hide();
        btn.unbind("click");
    }
}

function enableCategorySelection() {

    $('#card-player').find('.card-category').bind('click tap',function (event) {

        event.preventDefault();
        var cat = $(this).attr('data-id');
        chooseCategory(cat);
    });
}

function disableCategorySelection() {

    $('.card-category').removeClass('btn').unbind('click tap');
}

function preventNavigation(){
    return $('#abort-message').text();
}
/* Window Events */

// StartUp
$(document).ready(function () {
    update();
});

// Prevent Navigation while inGame
$(window).on('beforeunload', preventNavigation).on('unload', abortGame);

