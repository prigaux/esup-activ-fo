/*
 * jquery.qtip. The jQuery tooltip plugin
 *
 * Copyright (c) 2010 A. Anli
 * http://www.univ-paris1.fr
 *
 * Licensed under MIT
 * http://www.opensource.org/licenses/mit-license.php
 *
 * Launch  : september 2010
 * Version : 1.0.0
 */
 
$( document ).ready( function( ) {

	 $( '.helpTip' ).each( function( ) {
                  var helpMsg = $( this ).attr( 'longdesc' );
		  $(this).qtip({
		    content: helpMsg,
    		    style: { 
   		      width: 300,
		      padding: 3,
		      background: '#A2D959',
		      color: 'black',
		      textAlign: 'center',
		      border: {
			 width: 7,
			 radius: 5,
			 color: '#A2D959'
		    	 },
		      tip: 'topLeft',		    
   		      name: 'cream' // Inherit the rest of the attributes from the preset dark style
		      }
	            });
	} );
	 


} );