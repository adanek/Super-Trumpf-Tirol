function chooseCategory(category) {

    var url = "/game/play";
    $.post(url, {cid: category});
    
    disableCategories();
    setTimeout(update, 500);
    
}

function commitRound(){
    
    var url="/game/commit";
    $.post(url);
    setTimeout(update, 500);
}

function update() {
    updateStatus();
    updatePlayerCard();    
}

function updateStatus() {

    var url = "/game/status";
    $.get(url, function (state) {
        $('.game-info-cards-player').text(state.CardCountPlayer);
        $('.game-info-cards-competitor').text(state.CardCountCompetitor);
        $('#info-box').find('.message').text(state.Message);

        switch (state.State) {
            case "WaitForYourChoice":
                setStateChoise();
                break;
            case "WaitForOtherPlayer":
            case "WaitForCommit":
                setStateCommit();
                break;
            case "Won":

        }

    });
}

function setStateChoise() {
    
    updateCompetitiorCard();
    CommitButtonVisible(false);
    CompetitorsCardVisible(false);
    enableCategories();
  }

function setStateCommit() {
    
    CompetitorsCardVisible(true);
    CommitButtonVisible(true);
}

function CompetitorsCardVisible(val) {

    var front = $('.card-front');
    var back = $('.card-back');
    
    if(val)
    {
        back.hide();
        front.show();        
    }
    else
    {
        front.hide();
        back.height($('#card-player').height()).show();        
    }    
}

function CommitButtonVisible(val){
    
    var btn = $('#info-button-commit');
    if (val) {
        
        btn.click(commitRound);
        btn.show();
        
    } else {
        btn.hide();
        btn.unbind("click");
    }
}



function updatePlayerCard() {

    var url = "/game/card";
    $.get(url, function (card) {

        $('#card-player .card-title').text(card.name);
        $('#card-player img').attr('src', card.image);

        for (cid = 0; cid < card.categories.length; cid++) {
            var cat = card.categories[cid];
            var cls = "card-category-" + cat.name;
            var elm = $('#card-player .' + cls);
            elm.text(cat.value);
        }

    });
}

function updateCompetitiorCard() {

    var url = '/game/competitorcard';
    $.post(url, function (card) {

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

            // Set color
            if (cat.choosen) {
                $(selector).addClass('list-group-item-info');
            }

        }
    });


}

// Bind click-event on categories
function enableCategories() {

    $('#card-player').find('.card-category').click(function (event) {

        event.preventDefault();
        var cat = $(this).attr('data-id');
        chooseCategory(cat);
    });
}

function disableCategories(){

    $('.card-category').removeClass('btn').unbind('click');
}

/* Window Events */
$(document).ready(function () {

    update();
});


