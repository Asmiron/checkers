function sendData() {
    var text = document.getElementById("myText").value;
    var data = JSON.stringify({text: text});
    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/start", true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                console.log(xhr.responseText);
            } else {
                console.log("not 200");
                setTimeout(function() {
                    sendGetRequest();
                }, 5000);
            }
        }
    };
    xhr.send(data);
}

function sendGetRequest() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            var data = JSON.parse(xhr.responseText);
            writeToPage(data.text);
        }
    };
    xhr.open("GET", "/start/check", true);
    xhr.send();
}

function changeBackgroundColor() {
    document.body.style.backgroundColor = "green";
}

function writeToPage(text) {
    var targetElement = document.getElementById("textElement");
    targetElement.innerHTML = text;
}