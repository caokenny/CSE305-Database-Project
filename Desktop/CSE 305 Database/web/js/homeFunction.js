function openPage(evt, PageID) {
    // Declare all variables
    var i, tabcontent, tablinks;

    // Get all elements with class="tabcontent" and hide them
    tabcontent = document.getElementsByClassName("tabcontent");
    for (i = 0; i < tabcontent.length; i++) {
        tabcontent[i].style.display = "none";
    }

    // Get all elements with class="tablinks" and remove the class "active"
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].className = tablinks[i].className.replace(" active", "");
    }

    // Show the current tab, and add an "active" class to the link that opened the tab
    var elementExists = document.getElementById("Login");
    if (elementExists)
        elementExists.style.display = "none";
    var otherElementExists = document.getElementById("Roundtrip");
    if (otherElementExists)
        otherElementExists.style.display = "none";
    var otherotherElementExists = document.getElementById("Employee");
    if (otherotherElementExists)
        otherotherElementExists.style.display = "none";
    document.getElementById(PageID).style.display = "block";
    evt.currentTarget.className += " active";
}

function forceLower(strInput) {
    strInput.value = strInput.value.toLowerCase();
}

function callServlet(servlet) {
    document.forms[0].action = servlet;
    document.forms[0].submit();
}