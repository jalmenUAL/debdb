package debdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import de.f0rce.ace.AceEditor;
import de.f0rce.ace.enums.AceMode;
import de.f0rce.ace.enums.AceTheme;

/**
 * A sample Vaadin view class.
 * <p>
 * To implement a Vaadin view just extend any Vaadin component and use @Route
 * annotation to announce it in a URL as a Spring managed bean.
 * <p>
 * A new instance of this class is created for every new user and every browser
 * tab/window.
 * <p>
 * The main view contains a text field for getting the user name and a button
 * that shows a greeting message in a notification.
 */
@Route
public class MainView extends VerticalLayout {

	 
	Integer step = 0;
	de.f0rce.ace.AceEditor editor = new de.f0rce.ace.AceEditor();
	 

	public MainView() {

		System.setProperty("java.library.path", "/Applications/SWI-Prolog.app/Contents/swipl/lib");
		VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
		final VerticalLayout layout = new VerticalLayout();
		layout.getStyle().set("width", "100%");
		layout.getStyle().set("background", "#F8F8F8");
		Image lab = new Image("img/bannerspl.png", "banner");
		lab.setWidth("100%");
		lab.setHeight("200px");
		HorizontalLayout lfile = new HorizontalLayout();
	 

		Button run = new Button("Run");
		run.setWidth("100%");
		run.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button debug = new Button("Debug");
		debug.setWidth("100%");
		debug.addThemeVariants(ButtonVariant.LUMO_ERROR);

		VerticalLayout edS = new VerticalLayout();
		VerticalLayout edP = new VerticalLayout();

		editor.setHeight("300px");
		editor.setWidth("100%");
		editor.setFontSize(18);
		editor.setMode(AceMode.sparql);
		editor.setTheme(AceTheme.eclipse);
		editor.setUseWorker(true);
		editor.setReadOnly(false);
		editor.setShowInvisibles(false);
		editor.setShowGutter(false);
		editor.setShowPrintMargin(false);
		editor.setSofttabs(false);

		 
		
		String prefix = "PREFIX dbo:<http://dbpedia.org/ontology/>"
				+ "PREFIX dbr:<http://dbpedia.org/resource/>"
				+ "PREFIX dbp:<http://dbpedia.org/property/>"
				+ "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
				+ "PREFIX yago:<http://dbpedia.org/class/yago/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

		String exampleA = "SELECT ?Country WHERE { "
				+ " ?Country rdf:type yago:WikicatCountriesInEurope ."
				+ " ?Country dbo:currency dbr:Euro ."
				+ " ?Country dbo:officialLanguage dbr:Italian_language ."
				+ " ?Country dbo:populationTotal ?Population ."
				+ " FILTER(?Population>=100000) "
				+ " }";

		String exampleB = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleC = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleD = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleE = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";
		String exampleF = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleG = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleH = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		String exampleI = "SELECT ?Country WHERE { "
				+ "  ?Country rdf:type yago:WikicatCountriesInEurope"
				+ "  }";

		AceEditor editorP = new AceEditor();
		editorP.setHeight("300px");
		editorP.setWidth("100%");
		editorP.setFontSize(18);
		editorP.setMode(AceMode.prolog);
		editorP.setTheme(AceTheme.eclipse);
		editorP.setUseWorker(true);
		editorP.setReadOnly(true);
		editorP.setShowInvisibles(false);
		editorP.setShowGutter(false);
		editorP.setShowPrintMargin(false);
		editorP.setSofttabs(false);
		

		editor.setValue(exampleA);
		
		Grid<HashMap<String, org.jpl7.Term>> answers = new Grid<HashMap<String, org.jpl7.Term>>();
		answers.setWidth("100%");
		answers.setHeight("100%");
		answers.setVisible(true);
		
		VerticalLayout lanswers = new VerticalLayout();
		lanswers.setWidth("100%");
		lanswers.setHeight("200pt");
		lanswers.setVisible(true);

		Grid<HashMap<String, String>> answers1 = new Grid<HashMap<String, String>>();
		answers1.setWidth("100%");
		answers1.setHeight("100%");
		answers1.setVisible(true);
		
		
		
		
		VerticalLayout lanswers1 = new VerticalLayout();
		lanswers1.setWidth("100%");
		lanswers1.setHeight("200pt");
		lanswers1.setVisible(true);
		
		Grid<HashMap<String, String>> answers2 = new Grid<HashMap<String, String>>();
		answers2.setWidth("100%");
		answers2.setHeight("100%");
		answers2.setVisible(true);
		
		VerticalLayout lanswers2 = new VerticalLayout();
		lanswers2.setWidth("100%");
		lanswers2.setHeight("200pt");
		lanswers2.setVisible(true);
		
		
		
		List<HashMap<String, String>> rows = new ArrayList<>();

		MenuBar menuBar = new MenuBar();
		menuBar.setWidth("100%");
		ComponentEventListener<ClickEvent<MenuItem>> listener = e -> {

			answers1.removeAllColumns();
			editorP.clear();

			if (e.getSource().getText().equals("Example A")) {

				editor.setValue(exampleA);

			} else if (e.getSource().getText().equals("Example B")) {

				editor.setValue(exampleB);

			} else if (e.getSource().getText().equals("Example C")) {

				editor.setValue(exampleC);

			} else if (e.getSource().getText().equals("Example D")) {

				editor.setValue(exampleD);

			} else if (e.getSource().getText().equals("Example E")) {

				editor.setValue(exampleE);

			} else if (e.getSource().getText().equals("Example F")) {

				editor.setValue(exampleF);

			} else if (e.getSource().getText().equals("Example G")) {

				editor.setValue(exampleG);

			} else if (e.getSource().getText().equals("Example H")) {

				editor.setValue(exampleH);

			} else if (e.getSource().getText().equals("Example I")) {

				editor.setValue(exampleI);

			}

		}

		;

		MenuItem examples = menuBar.addItem("Examples", listener);
		SubMenu basicSubMenu = examples.getSubMenu();
		basicSubMenu.addItem("Example A", listener);
		basicSubMenu.addItem("Example B", listener);
		basicSubMenu.addItem("Example C", listener);
		basicSubMenu.addItem("Example D", listener);
		basicSubMenu.addItem("Example E", listener);
		basicSubMenu.addItem("Example F", listener);
		basicSubMenu.addItem("Example G", listener);
		basicSubMenu.addItem("Example H", listener);
		basicSubMenu.addItem("Example I", listener);

		run.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				String t = "";
				ResultSet result;
				// Define the DBpedia SPARQL endpoint
				String service = "http://dbpedia.org/sparql";

				 
				// Create a query execution object
				Query query = QueryFactory.create(prefix+editor.getValue());
				
				
				pSPARQL ps = new pSPARQL();

				List<List<String>> rules = ps.SPARQLtoProlog(prefix+editor.getValue(), 0);
				
				String pp = "";
				String prule = "";
				for (List<String> r : rules) {
					prule = r.get(0) + ":-";
					for (int i = 1; i < r.size(); i++) {
						prule = prule + "\n       " + r.get(i) + ",";
					}
					prule = prule.substring(0, prule.length() - 1) + ".";
					pp = pp + "\n" + prule;

				}
				
				editorP.setValue(pp);
				
				try (QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query)) {
					// Execute the query and obtain results
					ResultSet results = qexec.execSelect();

					answers1.removeAllColumns();
					answers2.removeAllColumns();
					List<String> variables = results.getResultVars();

					rows.clear();
					while (results.hasNext()) {
						QuerySolution solution = results.next();
						LinkedHashMap<String, String> sol = new LinkedHashMap<String, String>();
						for (String vari : variables) {

							if (solution.get(vari) == null) {
								sol.put(vari, " ");
							} else
								sol.put(vari, solution.get(vari).toString());
						}
						rows.add(sol);
					}
					answers1.setItems(rows);
					if (rows.size() > 0) {
						lanswers1.setVisible(true);
						answers1.setVisible(true);

						editorP.setVisible(true);
						HashMap<String, String> sr = rows.get(0);

						for (Map.Entry<String, String> entry : sr.entrySet()) {

							answers1.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
									.setResizable(true).setSortable(true)
									.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
											& isNumeric(y.get(entry.getKey()).toString())
													? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
															Float.parseFloat(y.get(entry.getKey()).toString()))
													: x.get(entry.getKey()).toString()
															.compareTo(y.get(entry.getKey()).toString()));

						}
					} else {

						editorP.setVisible(true);
						show_notification("Successful!", "No answer found");
					}

				}
			}

		});

		

		debug.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				// TODO Auto-generated method stub
				/*
				step = 0; // step++;
				pSPARQL ps = new pSPARQL();
				String s = editor.getValue();
				List<List<String>> rules = null;
				rules = ps.SPARQLtoProlog(s, step);

				String pp = "";
				String prule = "";
				for (List<String> r : rules) {
					prule = r.get(0) + ":-";
					for (int i = 1; i < r.size(); i++) {
						prule = prule + "\n       " + r.get(i) + ",";
					}
					prule = prule.substring(0, prule.length() - 1) + ".";
					pp = pp + "\n" + prule;

				}*/
				answers2.setItems(rows);
				lanswers2.setVisible(true);
				answers2.setVisible(true);
				List<HashMap<String, String>> rows2 = new ArrayList<>();
				answers1.addItemClickListener(event2->{Notification.show(event2.getItem().toString()); rows2.add(event2.getItem());answers2.setItems(rows);});
				
				/*if (rows2.size() > 0) {
					lanswers2.setVisible(true);
					answers2.setVisible(true);

					editorP.setVisible(true);
					HashMap<String, String> sr = rows2.get(0);

					for (Map.Entry<String, String> entry : sr.entrySet()) {

						answers2.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
								.setResizable(true).setSortable(true)
								.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
										& isNumeric(y.get(entry.getKey()).toString())
												? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
														Float.parseFloat(y.get(entry.getKey()).toString()))
												: x.get(entry.getKey()).toString()
														.compareTo(y.get(entry.getKey()).toString()));

					}
				} else {

					editorP.setVisible(true);
					show_notification("Error","Please select at least one answer");
				}*/

				/*
				String t1 = "use_module(library(semweb/rdf11))";
				org.jpl7.Query q1 = new org.jpl7.Query(t1);
				System.out.print((q1.hasSolution() ? "" : ""));
				q1.close();

				String t11 = "use_module(library(semweb/rdf_http_plugin))";
				org.jpl7.Query q11 = new org.jpl7.Query(t11);
				System.out.print((q11.hasSolution() ? "" : ""));
				q11.close();

				String t12 = "use_module(library(lists))";
				org.jpl7.Query q12 = new org.jpl7.Query(t12);
				System.out.print((q12.hasSolution() ? "" : ""));
				q12.close();

				String t21b = "rdf_reset_db";
				org.jpl7.Query q21b = new org.jpl7.Query(t21b);
				System.out.print((q21b.hasSolution() ? "" : ""));
				q21b.close();

				String t21bb = "rdf_reset_db";
				org.jpl7.Query q21bb = new org.jpl7.Query(t21bb);
				System.out.print((q21bb.hasSolution() ? "" : ""));
				q21bb.close();

				String t21c = " working_directory(_,\"C:/\")";
				org.jpl7.Query q21c = new org.jpl7.Query(t21c);
				System.out.print((q21c.hasSolution() ? "" : ""));
				q21c.close();

				String t2 = "rdf_load('" + "C:/tmp-sparql/model.rdf" + "')";
				org.jpl7.Query q2 = new org.jpl7.Query(t2);
				System.out.print((q2.hasSolution() ? "" : ""));
				q2.close();

				editorP.setValue(pp);

				String prule2 = "";
				System.out.println("Number of rules: " + rules.size());
				for (List<String> r : rules) {

					prule2 = r.get(0) + ":-";
					for (int i = 1; i < r.size(); i++) {
						prule2 = prule2 + r.get(i) + ',';
					}
					prule2 = prule2.substring(0, prule2.length() - 1);
					String aprule = "asserta((" + prule2 + "))";
					org.jpl7.Query q3 = new org.jpl7.Query(aprule);
					System.out.println((q3.hasSolution() ? aprule : ""));
					q3.close();

				}

				String[] ops = {
						"'http://www.w3.org/2001/XMLSchema#decimal'(X^^TX,Y^^'http://www.w3.org/2001/XMLSchema#decimal'):-!, Y=X ",
						"'http://jena.apache.org/ARQ/function#sqrt'(X^^TX,Y^^TX):-!, Y is sqrt(X) ",
						"if(X,Y,Z,T):-!,((X=1^^_)->T=Y;T=Z)",
						"call_function(X,Y,F,T):-!, X=..[_,TX,TYPE],Y=..[_,TY|_],NE=..[F,TX,TY],TAUX is NE,T=..['^^',TAUX,'http://www.w3.org/2001/XMLSchema#decimal']" };

				for (int i = 0; i < ops.length; i++) {
					String aprule = "asserta((" + ops[i] + "))";
					org.jpl7.Query q3 = new org.jpl7.Query(aprule);
					System.out.println((q3.hasSolution() ? aprule : ""));
					q3.close();
				}

				List<HashMap<String, org.jpl7.Term>> rows = new ArrayList<>();

				answers1.removeAllColumns();

				org.jpl7.Atom t = new org.jpl7.Atom("Null");
				org.jpl7.Query q3 = new org.jpl7.Query(rules.get(0).get(0));
				Map<String, org.jpl7.Term>[] sols = q3.allSolutions();
				q3.close();

				for (Map<String, org.jpl7.Term> solution : sols) {
					Set<String> sol = solution.keySet();
					for (String var : sol) {
						if (solution.get(var).isCompound()) {
							solution.put(var, solution.get(var).arg(1));
						}
						if (solution.get(var).isVariable()) {
							solution.put(var, t);
						}
					}
				}

				for (Map<String, org.jpl7.Term> solution : sols) {
					rows.add((HashMap<String, org.jpl7.Term>) solution);

				}
				System.out.println("Yes: answers " + sols.length);

				answers.setItems(rows);

				if (rows.size() > 0) {
					HashMap<String, org.jpl7.Term> sr = rows.get(0);

					for (Map.Entry<String, org.jpl7.Term> entry : sr.entrySet()) {
						answers1.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey());
					}
				}

				for (List<String> r : rules) {

					String dr = r.get(0);
					org.jpl7.Query drq = new org.jpl7.Query("retractall(" + dr + ")");
					System.out.println((drq.hasSolution() ? drq : ""));
					drq.close();

				}

				for (int i = 0; i < ops.length; i++) {
					String aprule = "retract((" + ops[i] + "))";
					org.jpl7.Query q4 = new org.jpl7.Query(aprule);
					System.out.println((q4.hasSolution() ? aprule : ""));
					q4.close();
				}*/

			}

		});

		edS.add(editor);
		edP.add(editorP);
		layout.add(lab);
		layout.add(new Label("Please select an example"));
		layout.add(menuBar);

		layout.add(lfile);

		 

		layout.add(edS);
		layout.add(run);
		layout.add(debug);
		lanswers1.add(answers1);
		layout.add(lanswers1);
		lanswers2.add(answers2);
		layout.add(lanswers2);

		layout.add(edP);

		editor.setLiveAutocompletion(true);
		editorP.setVisible(true);
		add(layout);
		this.setSizeFull();

	}

	public void show_notification(String type, String message) {
		Notification notification = Notification.show(type + " " + message);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Notification.Position.MIDDLE);
	}

	public void autocompletion() {

		List<String> l = new ArrayList<String>();
		l.add("SELECT");
		l.add("WHERE");
		l.add("FILTER");
		l.add("HAVING");
		l.add("BIND");
		l.add("ORDER BY");
		l.add("LET");
		editor.setCustomAutocompletion(l);

	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			Float.parseFloat(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}
