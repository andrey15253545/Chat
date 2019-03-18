;(function(){

    const POST = 'POST';
    const GET = 'GET';
    const DELETE = 'DELETE';
    const CHAT_CONTROLLER = 'chat';
    const USER_CONTROLLER = 'users';
    const MESSAGE_CONTROLLER = 'message';

    var a = 0;

    window.onload = function asd() {
        createChatWindow();
    };

	var ws = new WebSocket("ws://localhost:8080/socketHandler");

	var addFindCompanionButton;

	var WebSocketRequest = {
        reg: function(role, name) { ws.send(role + name); },
        end: function () { ws.send("/end"); },
        exit: function () { ws.send("/exit"); },
        start: function () { ws.send("/s"); },
        sendMessage: function (event) {
            if (event.keyCode === 13 && event.target.value !== '') {
                ws.send(event.target.value);
                createMessage('right', event.target.value);
                event.target.value = "";
            }
        }
    };

	var XHRRequest = {
	    id : "",
        reg: function(role, name) {
            if (role === '/a '){
                createXHRRequest(POST, USER_CONTROLLER, "", JSON.stringify({name: name, role: "AGENT"}))
                    .then(function (e) {
                        XHRRequest.id = JSON.parse(e.target.response).id;
                        responseHandler("You logged as an agent " + name );
                    });
            } else if (role === '/c ') {
                createXHRRequest(POST, USER_CONTROLLER, "", JSON.stringify({"name": name ,"role": "CLIENT"}))
                    .then(function (e) {
                        XHRRequest.id = JSON.parse(e.target.response).id;
                        responseHandler("You logged as a client" + name );
                    });
            }
        },
        start: function () {
            createXHRRequest(POST, CHAT_CONTROLLER, XHRRequest.id)
                .then(function (e) {
                    responseHandler(JSON.parse(e.target.response).message);
                })
        },
        end: function () {
            createXHRRequest(DELETE, CHAT_CONTROLLER, XHRRequest.id)
                .then(function (e) {
                    responseHandler(JSON.parse(e.target.response).message);
                })
        },
        exit : function () {
            createXHRRequest(DELETE, USER_CONTROLLER, XHRRequest.id)
                .then(function (e) {
                    responseHandler(JSON.parse(e.target.response).message);
                })
        },
        sendMessage : function (event) {
            if (event.keyCode === 13 && event.target.value !== '') {
                var data = JSON.stringify({message: event.target.value});
                createXHRRequest(POST, MESSAGE_CONTROLLER, XHRRequest.id, data)
                    .then(function (e) {
                        responseHandler(JSON.parse(e.target.response).message);
                    });
                createMessage('right', event.target.value);
                event.target.value = "";
            }
        },
        newMessages : function () {
            createXHRRequest(GET, MESSAGE_CONTROLLER, XHRRequest.id + '/new')
                .then(function (e) {
                    JSON.parse(e.target.response)
                        .forEach(function (t) {
                            responseHandler(t.text)
                        });
                })
        }
    };

    function createXHRRequest(method, controller, param, data){
        var url = 'http://localhost:8080/chat-1.0-SNAPSHOT';
        return new Promise(function (resolve, reject) {
            var xhr = new XMLHttpRequest();
            xhr.open(method, url +'/'+ controller + '/' + param, true);
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.onload = resolve;
            xhr.onerror = reject;
            xhr.send(data);
        })
    }

	// ????????????????????

	var request;
	if (a) {
	    request = WebSocketRequest;
    } else {
	    request = XHRRequest;
    }

    function responseHandler(response) {
        if (response.startsWith('You logged as a')) {
            removeReg();
            addMenu();
            addChatBody();
            addFindCompanionButton();
        } else if (response === "please, wait") {
            removeButton();
            addLeaveQueue();
        } else if (response.startsWith("you left the queue")) {
            removeButton();
            addFindCompanionButton();
        } else if (response.startsWith("dialog created with")) {
            removeButton();
            addInput();
            if (a===0) {
                addNewMessageBtn();
            }
        } else if (response === 'exited') {
            exitFromChat();
        } else if (response.endsWith('left the dialog')) {
            $('#inputTextField').remove();
            addFindCompanionButton();
        } else if (response.startsWith('command') && response.endsWith('not found')) {
            return;
        }
        if (response !== 'message send') {
            createMessage('left', response)
        }
    }
    
    ws.onmessage = function (event) {
        responseHandler(event.data);
    };

	function exitFromChat() {
        $('#inputTextField').remove();
        $('#bigButton').remove();
        $('#chatBody').remove();
        $('#dropDown').remove();
        $('#newMessageBtn').remove();
        addReg();
    }

	function addNewMessageBtn() {
        $('#chatBody').css('height', '66%');
        jQuery('<div/>', {class:'button', id: 'newMessageBtn'})
            .append('getNewMessage')
            .appendTo('#chat')
            .css('height','7%')
            .css('width','99%')
            .click(function () {
                request.newMessages();
            });
    }

    function createMessage(location, text) {
        jQuery('<div/>', { class: 't '+location })
            .append(text)
            .appendTo('#chatBody');
    }
    
	function addInput() {
        jQuery('<textarea/>', { class: 'textArea', id: 'inputTextField'})
            .appendTo('#input')
            .keyup( function(event) {
                request.sendMessage(event)
            })
	}

	function createChatWindow(){
        jQuery('<div/>', { class: 'chat', id: "chat" }). appendTo('body');
        jQuery('<div/>', { class: 'chatHead', id: "chatHead" }). appendTo('#chat');
        jQuery('<div/>', { class: 'minimize', id: "minimize" }). appendTo('#chatHead');
        jQuery('<div/>', { class: 'input', id: "input" }). appendTo('#chat');
        addReg();
        $(".chat").draggable({handle: ".chatHead"});
	}

	function addMenu(){
        jQuery('<div/>', { class: 'dropDown', id: 'dropDown' }). appendTo('#chatHead');
        jQuery('<div/>', { class: 'mainMenu', id: 'mainMenu' }). appendTo('#dropDown').append('menu');
        jQuery('<div/>', { class: 'dropDownChild', id: 'dropDownChild' }). appendTo('#dropDown');
        jQuery('<div/>', { class: 'button', id: 'exitBtn' })
            .click(function(){ request.exit() })
            .append("exit"). appendTo('#dropDownChild');
    }

	function addReg(){

	    var input = document.createElement("input");
	    input.style.outline = "none";
        jQuery('<div/>', { class: 'regField', id: 'regField' })
            .append('enter your name' , input)
            .appendTo('#chat');

        jQuery('<table/>', { id: 'table' }). appendTo('#input');
        jQuery('<tr/>', { id: 'tr' }). appendTo('#table');
        jQuery('<td/>', { id: 'tdRight' } ). appendTo('#tr');
        jQuery('<td/>', { id: 'tdLeft' }). appendTo('#tr');
        jQuery('<div/>', { class: 'button', id: 'regAgentBtn'})
            .click(function() {reg(input.value, "/a ", "find client")})
            .append('AGENT'). appendTo('#tdRight');

        jQuery('<div/>', {class: 'button', id: 'regClientBtn'})
            .click(function() {reg(input.value, "/c ", "find agent")})
            .append('CLIENT').appendTo('#tdLeft');
	}

    function reg(name, role, text){
        var re = /^\w+$/;
        if (!re.test(name.trim())) {
            alert('Invalid Text');
        } else {
            request.reg(role, name.trim());
            addFindCompanionButton = makeStartButton(text);
        }
    }

	function removeReg(){
		$("#regField").remove();
		$("#table").remove();
	}

    function addChatBody() {
	    jQuery('<div/>', {class: 'chatBody', id: 'chatBody' }).appendTo('#chat');
        if (a===0) {
            addNewMessageBtn();
        }
    }

    function makeStartButton(title){
		var text = title;
        return function () {
            jQuery('<div/>', {class: 'button', id: 'bigButton'})
                .html(text)
                .appendTo('#input')
                .click( function() {request.start()});
        }
    }

    function removeButton() {
        $('#bigButton').remove();
    }

    function addLeaveQueue(){
        jQuery('<div/>', {class: 'button', id: 'bigButton'})
            .html('leave the queue')
            .appendTo('#input')
            .click( function() {request.end()});
    }

}());