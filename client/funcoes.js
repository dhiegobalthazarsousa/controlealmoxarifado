<script>
		$(document).ready(function(){
		
			$('#form-incluir').submit(function(e){
				e.preventDefault();
				var descricao = $('#material-inclusao').val().trim();
				var valorUnitario = $('#valor-unitario-inclusao').val().trim();
				var quantidade = $('#quantidade-inclusao').val().trim();
				$.getJSON("http://127.0.0.1:4567/almoxarifado/inserir/"+descricao+"/"+valorUnitario+"/"+quantidade+"?format=json&jsoncallback=",function(data){
					if(data[0].resposta == "valid"){
						$('#output').html('<div class="alert alert-success" role="alert"><b>Material incluido com sucesso!</b></div>');
					}
					else{
						$('#output').html('<div class="alert alert-danger" role="alert"><b>Verifique se o material já está incluso!</b></div>');
					}
				});
			});
			
			$('#form-buscar').submit(function(e){
				e.preventDefault();
				$('#output').html('');
				var material = $('#material-busca').val().trim();
				var i;
				var cor;
				if(material != ""){
					$.getJSON("http://127.0.0.1:4567/almoxarifado/getmateriais/"+material+"?format=json&jsoncallback=",function(data){
						if(data[0].response == "-1"){
							$('#output').append('<div class="alert alert-danger" role="alert"><b>Material não encontrado</b></div>');
						}else{
							$('#output').append('<div class="row row-result-cabecalho"><div class="col-md-3">Material</div><div class="col-md-2">Valor Unitário</div><div class="col-md-2">Quantidade</div><div class="col-md-2">Saldo</div></div>');
							for(i=0; i<data.length; i++){
								if(i%2 == 0){
									cor='';							
								}
								else{
									cor='blue-default';
								}
								$('#output').append('<div class="row row-result '+cor+'"><div class="col-md-3">'+data[i].descricao.replace(/%20/g, " ")+'</div><div class="col-md-2">R$ '+data[i].valorunitario+'</div><div class="col-md-2">'+data[i].quantidade+'</div><div class="col-md-2">R$ '+data[i].saldo+'</div></div>');
							}
						}	
					});
				}else{
					$.getJSON("http://127.0.0.1:4567/almoxarifado/allmateriais?format=json&jsoncallback=",function(data){
						$('#output').append('<div class="row row-result-cabecalho"><div class="col-md-3">Material</div><div class="col-md-2">Valor Unitário</div><div class="col-md-2">Quantidade</div><div class="col-md-2">Saldo</div></div>');
						for(i=0; i<data.length; i++){
							if(i%2 == 0){
								cor='';							
							}
							else{
								cor='blue-default';
							}
							$('#output').append('<div class="row row-result '+cor+'"><div class="col-md-3">'+data[i].descricao.replace(/%20/g, " ")+'</div><div class="col-md-2">R$ '+data[i].valorunitario+'</div><div class="col-md-2">'+data[i].quantidade+'</div><div class="col-md-2">R$ '+data[i].saldo+'</div></div>');
						}
					});
				}
			});
			
			$('#tab1').click(function(){
				$('#output').html('');
			});
			$('#tab2').click(function(){
				$('#output').html('');
			});
			$('#tab3').click(function(){
				$('#output').html('');
			});
			$('#tab4').click(function(){
				$('#output').html('');
			});
		});
	</script>