package Controler;

import Model.EspecMaterial;
import Model.EspecMovimentacao;
import Model.Formata;
import Model.Material;
import Model.Model;
import Model.Movimentacao;
import Model.Nl;
import com.db4o.ObjectSet;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import spark.Request;
import spark.Response;
import spark.Route;
import static spark.Spark.get;


public class REST {

	private Model model;

	public REST(Model store) {
		this.model = store;
	}

	public void makeRoute() {

		get(new Route("/almoxarifado/inserirmaterial/:descricao/:valorunitario/:quantidade") {
			@Override
			public Object handle(Request request, Response response) {

				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject = new JSONObject();
					if (model.addMaterial(new Material(new EspecMaterial(request.params(":descricao")),
							Double.valueOf(request.params(":valorunitario")),
							Integer.valueOf(request.params(":quantidade")))))
						jsonObject.put("response", "0");
					else {
						jsonObject.put("response", "-1");
					}
					jsonArray.put(jsonObject);
					return jsonArray;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		get(new Route("/almoxarifado/allmateriais") {
			@Override
			public Object handle(Request request, Response response) {

				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					ObjectSet<Material> result = model.getAllMateriais();
					for (Material m : result) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("descricao", m.getEspecMaterial().getDescricao());
						jsonObject.put("valorunitario", Formata.formatOut(m.getValorUnitario()));
						jsonObject.put("quantidade", m.getQuantidade());
						jsonObject.put("saldo", Formata.formatOut(m.getSaldo()));
						jsonArray.put(jsonObject);
					}
					return jsonArray;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}

		});

		get(new Route("/almoxarifado/getmateriais/:descricao") {
			@Override
			public Object handle(Request request, Response response) {

				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					ObjectSet<Material> result = model.searchMaterial(request.params(":descricao"));
					if (!result.isEmpty()) {
						for (Material m : result) {
							JSONObject jsonObject = new JSONObject();
							jsonObject.put("response", "0");
							jsonObject.put("descricao", m.getEspecMaterial().getDescricao());
							jsonObject.put("valorunitario", Formata.formatOut(m.getValorUnitario()));
							jsonObject.put("quantidade", m.getQuantidade());
							jsonObject.put("saldo", Formata.formatOut(m.getSaldo()));
							jsonArray.put(jsonObject);
						}
					} else {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("response", "-1");
						jsonArray.put(jsonObject);
					}
					return jsonArray;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		get(new Route("/almoxarifado/movimentar/:descricao/:quantidade/:numeronl/:cessionario") {
			@Override
			public Object handle(Request request, Response response) {

				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					JSONObject jsonObject = new JSONObject();
					ObjectSet<Material> result = model.searchMaterial(request.params(":descricao"));
					if (!result.isEmpty()){
						Material mat = result.next();
						String cessionario = request.params(":cessionario");
						long numero = Long.valueOf(request.params(":numeronl"));
						int quantidade = Integer.valueOf(request.params(":quantidade"));
						if ((mat.getQuantidade()) >= quantidade) {
							Nl nl = new Nl(numero, cessionario);
							model.addMovimentacao(new Movimentacao(new EspecMovimentacao(mat, nl), quantidade));
							model.alterarMaterial(mat, (mat.getQuantidade() - quantidade));
							jsonObject.put("response", "0");
							model.addNl(nl);
						}else{
							jsonObject.put("response", "-2");
						}
					}else{
						jsonObject.put("response", "-1");
					}
					jsonArray.put(jsonObject);
					return jsonArray;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		get(new Route("/almoxarifado/allmovimentacoes") {
			@Override
			public Object handle(Request request, Response response) {
				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					ObjectSet<Movimentacao> result = model.getAllMovimentacoes();

					for (Movimentacao m : result) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("data", m.getEspecMovimentacao().getData());
						jsonObject.put("hora", m.getEspecMovimentacao().getHora());
						jsonObject.put("material",
								m.getEspecMovimentacao().getMaterial().getEspecMaterial().getDescricao());
						jsonObject.put("quantidade", m.getQuantidade());
						jsonObject.put("total", m.getTotal());
						jsonObject.put("nl", m.getEspecMovimentacao().getNl().toString());
						jsonObject.put("cessionario", m.getEspecMovimentacao().getNl().getCessionario());
						
						jsonArray.put(jsonObject);
					}
					return jsonArray;
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		get(new Route("/almoxarifado/searchmovimentacaobydata/:data") {
			@Override
			public Object handle(Request request, Response response) {
				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					ObjectSet<Movimentacao> result = model.searchMovimentacao(request.params(":data"));
					for (Movimentacao m : result) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("data", m.getEspecMovimentacao().getData());
						jsonObject.put("hora", m.getEspecMovimentacao().getHora());
						jsonObject.put("material",
								m.getEspecMovimentacao().getMaterial().getEspecMaterial().getDescricao());
						jsonObject.put("quantidade", m.getQuantidade());
						jsonObject.put("total", m.getTotal());
						jsonObject.put("nl", m.getEspecMovimentacao().getNl().toString());
						jsonObject.put("cessionario", m.getEspecMovimentacao().getNl().getCessionario());
						jsonArray.put(jsonObject);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});

		get(new Route("/almoxarifado/searchmovimentacaobynl/:nl") {
			@Override
			public Object handle(Request request, Response response) {
				response.header("Access-Control-Allow-Origin", "*");

				try {
					JSONArray jsonArray = new JSONArray();
					ObjectSet<Movimentacao> result = model.searchMovimentacaoByNl(request.params(":nl"));

					for (Movimentacao m : result) {
						JSONObject jsonObject = new JSONObject();
						jsonObject.put("material",
								m.getEspecMovimentacao().getMaterial().getEspecMaterial().getDescricao());
						jsonObject.put("quantidade", m.getQuantidade());
						jsonObject.put("total", m.getTotal());
						jsonObject.put("nl", m.getEspecMovimentacao().getNl());
						jsonObject.put("data", m.getEspecMovimentacao().getData());
						jsonObject.put("hora", m.getEspecMovimentacao().getHora());
						jsonArray.put(jsonObject);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return null;
			}
		});
		
		
	}
}