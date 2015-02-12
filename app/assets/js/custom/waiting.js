function tryStart(){
    
    var url = "/game/multi/ready";
    $.ajax({
        url: url, 
        type: 'post',
        cache: false
    }).done(function(data, statustext, xhr) {
        var status = xhr.status;
        
        if(status == 204){
            
            setTimeout(tryStart, 1000);
        }
        else {
            $(window).off('unload', abortGame);
            window.location = "/game";
        }
    });    
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

