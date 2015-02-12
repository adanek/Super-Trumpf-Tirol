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
            window.location = "/game";
        }
    });    
}

$(document).ready(tryStart);

