
var RelojID24 = null  
var RelojEjecutandose24 = false  
  
function DetenerReloj24 (){ 
    if(RelojEjecutandose24)  
        clearTimeout(RelojID24)  
    RelojEjecutandose24 = false  
}  
  
function MostrarHora24 () {  
    var ahora = new Date()  
    var horas = ahora.getHours()  
    var minutos = ahora.getMinutes()  
    var segundos = ahora.getSeconds()  
    var ValorHora  
  
    //establece las horas  
    if (horas < 10)
            ValorHora = "0" + horas  
    else  
        ValorHora = "" + horas  
  
    //establece los minutos  
    if (minutos < 10)  
        ValorHora += ":0" + minutos  
    else  
        ValorHora += ":" + minutos  
  
    //establece los segundos  
    if (segundos < 10)  
        ValorHora += ":0" + segundos  
    else  
        ValorHora += ":" + segundos  
          
	dia = ahora.getDate()
	mes = ahora.getMonth()
	anio = ahora.getYear()
	if (anio < 100)  
		anio = '19' + anio  
	else if ( ( anio > 100 ) && ( anio < 999 ) ) {  
		var cadena_anio = new String(anio)  
		anio = '20' + cadena_anio.substring(1,3)  
	}        
	var meses; 
	meses=new Array(12);
	meses[0]='Enero';
	meses[1]='Febrero';
	meses[2]='Marzo';
	meses[3]='Abril';
	meses[4]='Mayo';
	meses[5]='Junio';
	meses[6]='Julio';
	meses[7]='Agosto';
	meses[8]='Septiembre';
	meses[9]='Octubre';
	meses[10]='Noviembre';
	meses[11]='Diciembre';
	/* Seteo la hora ... */
	
   //document.reloj24.digitos.value = meses[mes] + " " + dia + ", " + anio + " " + ValorHora 
    document.getElementById('reloj').innerHTML  = meses[mes] + " " + dia + ", " + anio + " " + ValorHora
    RelojID24 = setTimeout("MostrarHora24()",1000)  
    RelojEjecutandose24 = true  
}  
  
function IniciarReloj24 () {  
    DetenerReloj24()  
    MostrarHora24()  
}  
  
window.onload = IniciarReloj24;  
if (document.captureEvents) {           //N4 requiere invocar la funcion captureEvents  
    document.captureEvents(Event.LOAD)  
}