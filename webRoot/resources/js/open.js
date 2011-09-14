var cat_seleccionada = 0;
function mostrar_categoria(cat) {

	if (cat_seleccionada != 0) {
		$('#div_categoria_'+cat_seleccionada).animate({height: 'toggle', opacity: 'toggle'}, 'slow');
	}

	if (cat == cat_seleccionada) {
		cat_seleccionada = 0;
		return;
	}
cat_seleccionada = cat;
$('#div_categoria_'+cat_seleccionada).animate({height: 'toggle', opacity: 'toggle'}, 'slow');
//$('#flecha_arriba_categoria_'+cat_seleccionada).toggle();
//$('#flecha_abajo_categoria_'+cat_seleccionada).toggle();
}

cont_seleccionado = 1;

function mostrar_contenido(c) {
	
	if (cont_seleccionado != 0) {
		$('#contenido_'+cont_seleccionado).animate({height: 'toggle', opacity: 'toggle'}, 'slow');
		
		$('#contenido_link_'+cont_seleccionado).removeClass('menu_activo_menu_colapsable');
		
		$('#simbolo_'+cont_seleccionado).html('[+]');
	}

	if (c == cont_seleccionado) {
		cont_seleccionado = 0;
		return;
	}
	cont_seleccionado = c;
	
	$('#contenido_'+cont_seleccionado).animate({height: 'toggle', opacity: 'toggle'}, 'slow');
	$('#contenido_link_'+cont_seleccionado).addClass('menu_activo_menu_colapsable');
	$('#simbolo_'+cont_seleccionado).html('[-]');
}

