function startGame(mode) {
    
    if(mode == 'S')
        startSinglePlayerGame();        
    else if(mode == 'M')
        startMultiPlayerGame();
}

function startSinglePlayerGame() {

    var url = "/game/create/single";
    doPost(url);
}

function startMultiPlayerGame() {

    var url = "/game/create/multi";
    doPost(url);
}

function doPost(url){

    $('<form action="#" method="post"></form>').attr('action', url).submit();
}