function startGame(mode) {
    
    $('#form-start-mode').val(mode);
    $('#form-start').submit();

}

function chooseCategory (category, url) {
    
    $.post(url, {cid:category});
}

$('.card-category').click(function (event) {
    event.preventDefault();
    var cat = $(this).attr('data-id');
    var url = $(this).attr('href');
    chooseCategory(cat, url);
});
