function startGame(mode) {
    
    $('#form-start-mode').val(mode);
    $('#form-start').submit();

}

function chooseCategory (category) {
    
    alert(category);
    
}

$('.card-category').click(function () {
    var cat = $(this).find('.card-content-label').text();
    chooseCategory(cat);
});
