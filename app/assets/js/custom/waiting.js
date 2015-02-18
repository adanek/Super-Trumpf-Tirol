function tryStart(){
    
    var url = "/game/multi/ready";
    $.ajax({
        url: url, 
        type: 'post',
        cache: false
    }).done(response).fail(response);
}

function response (data, statustext, xhr) {
    var status = xhr.status;

    if(status == 204){

        setTimeout(tryStart, 1000);
    } else {

        var loc = "/";

        if (status == 200) {
            loc = "/game";
        }

        $(window).off('unload', abortGame);
        window.location = loc;
    }
}
function setup() {
    $('#btn-cancel').click(function() {
        window.location.replace('/game/selectMode');
    });
    
    tryStart();
}

function abortGame() {

    var url = "/game/multi/abort";
    $.post(url);
}


$(document).ready(setup);
$(window).on('unload', abortGame);


