function vkHandler(resp) {
    if (resp.response) {
        var user = resp.response[0];
        return user.first_name + " " + user.last_name;
    }
}

function getVkName(id) {
    $.getJSON("https://api.vk.com/method/users.get?user_id=" + id + "&callback=?", vkHandler);
}
