function startGame(mode) {
    
    if(mode == 'S')
        startSinglePlayerGame();        
    else if(mode == 'M')
        startMultiPlayerGame();
}

function startSinglePlayerGame() {

    var url = "/game/single/create";
    doPost(url);
}

function startMultiPlayerGame() {

    var url = "/game/multi/create";
    doPost(url);
}

function doPost(url){

    $('<form action=' + url + ' method="post"></form>').appendTo('.body-content').submit();
}