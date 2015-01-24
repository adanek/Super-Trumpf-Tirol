/* Globals */


/* Section AJAX */

function chooseCategory(category) {

    var url = "/game/play";
    $.post(url, {cid: category});
   
    disableCategories();
    setTimeout(update, 500);
 }

function commitRound() {

    var url = "/game/commitround";
    $.post(url);
    setTimeout(update, 500);
}

function commitCard() {

    var url = "/game/commitcard";
    $.post(url);
    setTimeout(update, 500);
}

function getCompetitorsCard() {
    var url = '/game/competitorcard';
    $.post(url, updateCompetitiorCard);
}

function getCard() {

    var url = "/game/card";
    $.ajax({
        url: url,
        success:setCard,
        cache: false
    });
}

function getState() {

    var url = "/game/status";
    $.ajax({
        url: url,
        success:setState,
        cache: false
    });
}

function abortGame() {
    var url = "/game/abort";
    $.post(url);
    setTimeout(update, 500);
}

/* Section Logic */

function setState(state){

    $('.game-info-cards-player').text(state.CardCountPlayer);
    $('.game-info-cards-competitor').text(state.CardCountCompetitor);
    $('#info-box').find('.round').text(state.Round);
    $('#info-box').find('.message').text(state.Message);
    
    if(state.RoundState != "OUTSTANDING"){
        highlightCategory(state.ChoosenCategory, state.RoundState);
    }
    else{
        clearHighlighting();
    }

    switch (state.State) {
        case "WaitForYourChoice":
            setStateChoise();
            break;
        case "WaitForOtherPlayer":
            setStateWait();
            break;
        case "WaitForCommit":
            setStateCommit();
            break;
        case "Won":
        case "Lose":
            setStateEndGame();
            break;
    }
}

function update() {
    updateStatus();
}

function updateStatus() {

    getState();
}

function updatePlayerCard() {

    getCard();
}

function setCard(card) {
    
    $('#card-player').find('.card-title').text(card.name);
    $('#card-player').find('img').attr('src', card.image);

    for (cid = 0; cid < card.categories.length; cid++) {
        var cat = card.categories[cid];
        var selector = ".card-category-" + cat.name + '-value';
        $('#card-player').find(selector).text(cat.value);
    }
}

function updateCompetitiorCard(card) {

    // Set title
    $('#card-competitor .card-title').text(card.name);

    // Set image
    $('#card-competitor img').attr('src', card.image);

    // Set Categories
    for (var cid = 0; cid < card.categories.length; cid++) {

        var cat = card.categories[cid];
        var selector = '#card-competitor .card-category-' + cat.name;

        // Set Value
        $(selector + '-value').text(cat.value);
    }
}

function setStateChoise() {

    clearHighlighting();
    CompetitorsCardVisible(false);
    CommitRoundButtonVisible(false);
    CommitCardButtonVisible(false);
    
    updatePlayerCard();
    enableCategories();
}

function setStateWait() {

    clearHighlighting();
    CompetitorsCardVisible(false);
    CommitRoundButtonVisible(false);
    CommitCardButtonVisible(true);

    updatePlayerCard();
}

function setStateCommit() {

    getCompetitorsCard();
    CompetitorsCardVisible(true);
    CommitRoundButtonVisible(true);
    CommitCardButtonVisible(false);
}

function setStateEndGame(){

    window.onbeforeunload = null;
    CommitCardButtonVisible(false);
    CommitRoundButtonVisible(false);
    $("#cards").hide();
    $('#game-result').show();
    
}

function highlightCategory(category, roundstate) {

    var selector = '.card-category-' + category;

    if (roundstate == "WON") {
        $('#card-player').find(selector).addClass('list-group-item-success');
        $('#card-competitor').find(selector).addClass('list-group-item-danger');
    }
    else {
        $('#card-player').find(selector).addClass('list-group-item-danger');
        $('#card-competitor').find(selector).addClass('list-group-item-success');
    }
}

function clearHighlighting(){
    
    $('.card-category').removeClass('list-group-item-danger list-group-item-success');
    
}
/* Behavior */


function CompetitorsCardVisible(val) {

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

function CommitRoundButtonVisible(val) {

    var btn = $('#info-button-commit-round');
    if (val) {

        btn.click(commitRound);
        btn.show();

    } else {
        btn.hide();
        btn.unbind("click");
    }
}

function CommitCardButtonVisible(val) {

    var btn = $('#info-button-commit-card');
    if (val) {

        btn.click(commitCard);
        btn.show();

    } else {
        btn.hide();
        btn.unbind("click");
    }
}

function enableCategories() {

    $('#card-player').find('.card-category').bind('click tap',function (event) {

        event.preventDefault();
        var cat = $(this).attr('data-id');
        chooseCategory(cat);
    });
}

function disableCategories() {

    $('.card-category').removeClass('btn').unbind('click tap');
}

/* Window Events */

$(document).ready(function () {

    update();
});

window.onbeforeunload = function (){
    var msg = $('#abort-message').text();
    return msg;
};

window.addEventListener("unload", function () {
  abortGame();
});

$('game-result').find('button').click(function () {
    window.location.href = "/";
});