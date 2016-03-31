/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function init() {
    $('#menu').load('menu');
    $('#contenu').load('contenu');
    $('#pied').load('pied');
    
}

function listerPartie() {
    $('#contenu').load('partie/creer');
}

function rejoindrePartie(id) {
    $('#contenu').load('partie/rejoindre/'+id);
}

function rejoindrePost() {
    $.post(urlAppli + 'partie/rejoindre', $('form').serialize(), function success(data) {
        $('#contenu').html(data);
    });
}


