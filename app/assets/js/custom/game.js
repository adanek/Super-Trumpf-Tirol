function chooseCategory(category, url) {

    $.post(url, {cid: category});
    update();
}

$('.card-category').click(function (event) {

    event.preventDefault();
    var cat = $(this).attr('data-id');
    var url = $(this).attr('href');
    chooseCategory(cat, url);

});

function updateStatus() {

    var url = "/game/status";
    $.get(url, function (state) {
        $('.game-info-cards-player').text(state.CardCountPlayer);
        $('.game-info-cards-competitor').text(state.CardCountCompetitor);
        $('#info-box').find('.message').text(state.Message);
    });
}

function update() {
    updateStatus();
    updatePlayerCard();
    updateCompetitiorCard();
}

$(document).ready(function () {

    $('.card-back').height($('#card-player').height());
    update();
});

function showCompetitorsCard() {

    $('.card-front').toggle();
    $('.card-back').height($('#card-player').height()).toggle();
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

    $('.card-back').height($('#card-player').height());

}