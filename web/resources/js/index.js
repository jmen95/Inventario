/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function (){
    $("body").on("focus", ".mdl-textfield--floating-label", function () {
        $(this).addClass("is-upgraded is-focused");
    });
    $("body").on("focusout", ".mdl-textfield--floating-label", function () {
        if (($(this).find('input').val() && $(this).find('input').length > 0)) {
            $(this).removeClass("is-focused").addClass("is-dirty");
        } else {
            $(this).removeClass("is-focused");
        }
    });
});