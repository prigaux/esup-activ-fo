var c;
var formulaire=$("#accountForm").attr('action');
function croppie() {
	if (c==undefined){
	 c=new Croppie(document.getElementsByClassName('photo')[0], {
	     enableOrientation: true,
	     viewport: { width: 100, height: 123 },
	 });
	}
}

// Générer boutons rotation...
//Utilisation de <a class="RotatedPhotoLeft......"></a> à la place de button car au clic cela déclenche la sauvegarde du formulaire
if (!document.getElementById('navbar-inverse')){
	var span = document.createElement('span');
	span.innerHTML =
	['<div><nav class="navbar navbar-inverse"> <div><ul class="nav navbar-nav"><li><a style="font-size:13px;color:white" title="Rotation gauche"><i class="fa fa-rotate-right rotatedPhoto"></i></a></li><li><a style="font-size:13px;color:white" title="Rotation droite"><i class="fa fa-rotate-left rotatedPhotoLeft"></i></a></li><li><a style="font-size:13px;color:white" title="Valider la photo"><i class="fa fa-check exportedPhoto"></i></a></li></ul></div></nav></div>'].join('');
	document.getElementsByClassName("insertinnerHTMLRotation")[0].insertBefore(span, null);
}

$(".exportedPhoto").click(function () {
	if (formulaire.includes("/stylesheets/accountDataChange.faces") && $('.export').attr("src").substring("data:image/jpeg;base64,".length )!=""){
		exportImg(1);
		$('.croppie-container').hide();
		$('.insertinnerHTMLRotation').hide();
		$('.export').show();
	}
	else
		if ($('.photo').attr("src").substring("data:image/jpeg;base64,".length )!=""){
			exportImg(1);
		}
});

$(".rotatedPhoto").click(function () {
	c.rotate(90);
});

$(".rotatedPhotoLeft").click(function () {
	c.rotate(-90);
});


//Tester si le fichier est un fichier jpeg
function isJpeg(file, callback) {q
    var f = file;
      var fileReader = new FileReader();
      fileReader.onloadend = function(e) {
        var arr = (new Uint8Array(e.target.result)).subarray(0, 4);
        var header = "";
        for(var i = 0; i < arr.length; i++) {
           header += arr[i].toString(16);
        }
        switch (header) {
        //image/jpeg
          case "ffd8ffe0":
          case "ffd8ffe1":
          case "ffd8ffe2":
             return callback(true);

          default:
            return callback(false);
      }
     };
     fileReader.readAsArrayBuffer(f);
 }

function readFile(input) {
     if (input.files && input.files[0]) {
        $('.alert').hide();
		isJpeg(input.files[0], function(mime) {
		if(mime){
		  croppie(2);
		  var reader = new FileReader();
	      reader.onload = function (e) {
           var data = { src: e.target.result };
           EXIF.getData(data, function () {
               c.bind({
                   url: data.src,
                   orientation: data.exifdata && data.exifdata.Orientation,
                   zoom: 0, // minimal zoom level
               }).then(function(){
                   console.log('jQuery bind complete');
                   exportImg(2);
               });
           });
	      }
	      reader.readAsDataURL(input.files[0]);
		 }
		 else{
		   $('.alert-danger').show();
		   setTimeout(function(){ $('.alert-danger').hide(); }, 4000);
		 }
	 });
    }
    else {
        alert("Sorry - you're browser doesn't support the FileReader API");
    }
}

// Param=1 (onclick)
// Param=2 (onchange)
function exportImg(param) {
    c.result({
		type: 'base64',
		format: 'jpeg',
		size :{width: 283, height: 343},
    }).then(function (canvas) {
        document.getElementsByClassName("export")[0]. src = canvas;
		document.getElementsByClassName('setDataURL')[0].value=canvas.substring("data:image/jpeg;base64,".length );
		// Dans les procedures de mot de passe oublié et changement de mot de passe, la photo est toujours en mode modification. Lorsque l'utilisateur exporte (param=1)la photo, un message de prise en compte est affiché.
		// Pas d'utilisation d'affichage comme dans modification de données personnelles (modes consultation et modification)
		if ((formulaire.includes("/stylesheets/accountDataChange.faces")!="/stylesheets/accountDataChange.faces") && (param==1)){
			$('.alert-success').show();
			setTimeout(function(){ $('.alert-success').hide(); }, 3000);
		}
    });
}


//Tester si le fichier est un fichier jpeg
function isJpeg(file, callback) {
    var f = file;
      var fileReader = new FileReader();
      fileReader.onloadend = function(e) {
        var arr = (new Uint8Array(e.target.result)).subarray(0, 4);
        var header = "";
        for(var i = 0; i < arr.length; i++) {
           header += arr[i].toString(16);
        }
        switch (header) {
        //image/jpeg
          case "ffd8ffe0":
          case "ffd8ffe1":
          case "ffd8ffe2":
             return callback(true);

          default:
            return callback(false);
      }
     };
     fileReader.readAsArrayBuffer(f);
 }