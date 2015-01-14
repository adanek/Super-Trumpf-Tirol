function startGame(mode) {
    
    $('#form-start-mode').val(mode);
    //alert($('#form-start-mode').val());

    $('#form-start').submit();
    $('#response').html("done");
    //document.getElementById("form-start-mode").value = "1";
    //alert(document.getElementById("form-start-mode").value);
    //$("#start")    
}
