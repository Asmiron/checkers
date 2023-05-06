
function saveFormData(event) {
    // Get the form element
    event.preventDefault();
    let form = document.getElementById("form");

    // Create an object to store the form data
    let formData = {};


    // Loop over all the form fields and add their values to the formData object
    for (let i = 0; i < form.elements.length; i++) {
        let element = form.elements[i];
        if (element.type !== "submit") {
           formData[element.name] = element.value;
        }
    }

    // Convert the formData object to a JSON string

    let jsonData = JSON.stringify(formData);
    sendData(jsonData);
    alert("AGA");
}



function sendData(data) {
    var xhr = new XMLHttpRequest();
    console.log("xhr");
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
        if (xhr.readyState === 4 && xhr.status === 201) {
            let response = JSON.parse(xhr.responseText);
            let imagePath = response.imagePath;

            // Update the image src attribute with the new path
            let img = document.getElementById("myImage");
            img.src = imagePath;
        }
        else {
            // Request failed, handle error
            console.error('Image request failed');
        }
    };
    xhr.open("GET", "/start/check", true);
    xhr.send();
}


