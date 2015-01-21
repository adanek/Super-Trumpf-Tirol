function chooseCategory (category, url) {

    $.post(url, {cid:category});
    update();
}

$('.card-category').click(function (event) {

    event.preventDefault();
    var cat = $(this).attr('data-id');
    var url = $(this).attr('href');
    chooseCategory(cat, url);

});

function updateStatus(){

    var url = "/game/status";
    $.get(url, function (state){
        $('.game-info-cards-player').text(state.cardCount);
        $('.game-info-cards-competitor').text(state.cardCountCompetitor);
        

    });
}

function update(){
    updateStatus();
}

$(document).ready(function(){

    $('.card-back').height($('#card-player').height());
    update();
});

function showCompetitorsCard(){
    
    $('.card-front').toggle();
    $('.card-back').height($('#card-player').height()).toggle();
}