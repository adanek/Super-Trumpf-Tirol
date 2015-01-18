function startGame(mode) {
    
    $('#form-start-mode').val(mode);
    $('#form-start').submit();

}

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
        $('.info-roundnr').text(state.round);
        $('.info-message').text(state.message);
        $('.info-cardCount').text(state.cardCount);
        $('.info-competitorCardCount').text(state.cardCountCompetitor);        
    });
}

function update(){
    updateStatus();
}

$.load(update());
