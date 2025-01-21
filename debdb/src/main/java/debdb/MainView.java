package debdb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.jpl7.Term;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
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

@Route
public class MainView extends VerticalLayout {

	List<List<String>> rules;

	public MainView() {

		 
		VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
		final VerticalLayout layout = new VerticalLayout();
		layout.getStyle().set("width", "100%");
		layout.getStyle().set("background", "#F8F8F8");
		Image lab = new Image("img/bannerspl.png", "banner");
		lab.setWidth("100%");
		lab.setHeight("200px");

		Button run = new Button("Run");
		run.setWidth("100%");
		run.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button debug = new Button("Debug");
		debug.setWidth("100%");
		debug.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		debug.setVisible(false);
		Button addexpected = new Button("Add Expected Answer");
		addexpected.setWidth("100%");
		addexpected.addThemeVariants(ButtonVariant.LUMO_ERROR);
		addexpected.setVisible(false);
		Button addunexpected = new Button("Add Unexpected Answer");
		addunexpected.setWidth("100%");
		addunexpected.addThemeVariants(ButtonVariant.LUMO_ERROR);
		addunexpected.setVisible(false);
		Button find = new Button("Find Query");
		find.setWidth("100%");
		find.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		find.setVisible(false);
		Button removeexpected = new Button("Remove Expected");
		removeexpected.setWidth("100%");
		removeexpected.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeexpected.setVisible(false);
		Button removeunexpected = new Button("Remove Unexpected");
		removeunexpected.setWidth("100%");
		removeunexpected.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		removeunexpected.setVisible(false);
		

		VerticalLayout edS = new VerticalLayout();
		VerticalLayout edP = new VerticalLayout();
		edP.setVisible(false);

		AceEditor editorS = new de.f0rce.ace.AceEditor();
		editorS.setHeight("400px");
		editorS.setWidth("100%");
		editorS.setFontSize(18);
		editorS.setMode(AceMode.sparql);
		editorS.setTheme(AceTheme.eclipse);
		editorS.setUseWorker(true);
		editorS.setReadOnly(false);
		editorS.setShowInvisibles(false);
		editorS.setShowGutter(false);
		editorS.setShowPrintMargin(false);
		editorS.setSofttabs(false);
		autocompletion(editorS);

		String prefix = "PREFIX dbo:<http://dbpedia.org/ontology/>" + "PREFIX dbr:<http://dbpedia.org/resource/>"
				+ "PREFIX dbp:<http://dbpedia.org/property/>" + "PREFIX foaf:<http://xmlns.com/foaf/0.1/>"
				+ "PREFIX yago:<http://dbpedia.org/class/yago/>"
				+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
				+ "PREFIX owl: <http://www.w3.org/2002/07/owl#>"
				+ "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>";

		String exampleA = "SELECT ?Country WHERE { " + " ?Country rdf:type yago:WikicatCountriesInEurope ."
				+ " ?Country dbo:currency dbr:Euro ." + " ?Country dbo:officialLanguage dbr:Italian_language ."
				+ " ?Country dbo:populationTotal ?Population ." + " FILTER(?Population>=100000) " + " }";

		String exampleB = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleC = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleD = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleE = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";
		String exampleF = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleG = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleH = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		String exampleI = "SELECT ?Country WHERE { " + "  ?Country rdf:type yago:WikicatCountriesInEurope" + "  }";

		AceEditor editorP = new AceEditor();
		editorP.setHeight("400px");
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

		editorS.setValue(exampleA);

		Grid<HashMap<String, org.jpl7.Term>> answersP = new Grid<HashMap<String, org.jpl7.Term>>();
		answersP.setWidth("100%");
		answersP.setHeight("100%");

		VerticalLayout lanswersP = new VerticalLayout();
		lanswersP.setWidth("100%");
		lanswersP.setHeight("400pt");
		lanswersP.setVisible(false);

		Grid<HashMap<String, String>> answersS = new Grid<HashMap<String, String>>();
		answersS.setWidth("100%");
		answersS.setHeight("100%");

		VerticalLayout lanswersS = new VerticalLayout();
		lanswersS.setWidth("100%");
		lanswersS.setHeight("200pt");
		lanswersS.setVisible(false);

		Grid<HashMap<String, String>> expected = new Grid<HashMap<String, String>>();
		expected.setWidth("100%");
		expected.setHeight("100%");

		VerticalLayout lexpected = new VerticalLayout();
		lexpected.setWidth("100%");
		lexpected.setHeight("200pt");
		lexpected.setVisible(false);

		Grid<HashMap<String, String>> unexpected = new Grid<HashMap<String, String>>();
		unexpected.setWidth("100%");
		unexpected.setHeight("100%");

		VerticalLayout lunexpected = new VerticalLayout();
		lunexpected.setWidth("100%");
		lunexpected.setHeight("200pt");
		lunexpected.setVisible(false);

		List<HashMap<String, String>> rowsS = new ArrayList<>();
		List<HashMap<String, String>> rowsE = new ArrayList<>();
		List<HashMap<String, String>> rowsU = new ArrayList<>();

		List<HashMap<String, org.jpl7.Term>> rowsP = new ArrayList<>();
		HashMap<String, TextField> textfields = new HashMap<>();

		answersS.setSelectionMode(SelectionMode.MULTI);
		expected.setSelectionMode(SelectionMode.MULTI);
		unexpected.setSelectionMode(SelectionMode.MULTI);

		MenuBar menuBar = new MenuBar();
		menuBar.setWidth("100%");
		ComponentEventListener<ClickEvent<MenuItem>> listener = e -> {

			// answersS.removeAllColumns();
			editorP.clear();

			if (e.getSource().getText().equals("Example A")) {

				editorS.setValue(exampleA);

			} else if (e.getSource().getText().equals("Example B")) {

				editorS.setValue(exampleB);

			} else if (e.getSource().getText().equals("Example C")) {

				editorS.setValue(exampleC);

			} else if (e.getSource().getText().equals("Example D")) {

				editorS.setValue(exampleD);

			} else if (e.getSource().getText().equals("Example E")) {

				editorS.setValue(exampleE);

			} else if (e.getSource().getText().equals("Example F")) {

				editorS.setValue(exampleF);

			} else if (e.getSource().getText().equals("Example G")) {

				editorS.setValue(exampleG);

			} else if (e.getSource().getText().equals("Example H")) {

				editorS.setValue(exampleH);

			} else if (e.getSource().getText().equals("Example I")) {

				editorS.setValue(exampleI);

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

				String service = "http://dbpedia.org/sparql";

				debug.setVisible(true);
				addexpected.setVisible(false);
				addunexpected.setVisible(false);
				find.setVisible(false);
				lexpected.setVisible(false);
				lunexpected.setVisible(false);
				removeexpected.setVisible(false);
				removeunexpected.setVisible(false);
				lanswersP.setVisible(false);

				Query query = QueryFactory.create(prefix + editorS.getValue());

				pSPARQL ps = new pSPARQL();

				rules = ps.SPARQLtoProlog(prefix + editorS.getValue(), 0);

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
				System.out.println(pp);

				try (QueryExecution qexec = QueryExecutionFactory.sparqlService(service, query)) {

					ResultSet results = qexec.execSelect();

					answersS.removeAllColumns();
					expected.removeAllColumns();
					unexpected.removeAllColumns();

					List<String> variables = results.getResultVars();

					rowsS.clear();
					while (results.hasNext()) {
						QuerySolution solution = results.next();
						LinkedHashMap<String, String> sol = new LinkedHashMap<String, String>();
						for (String vari : variables) {

							if (solution.get(vari) == null) {
								sol.put(vari, " ");
							} else
								sol.put(vari, solution.get(vari).toString());
						}
						rowsS.add(sol);
					}
					answersS.setItems(rowsS);

					if (rowsS.size() > 0) {

						lanswersS.setVisible(true);

						HashMap<String, String> sr = rowsS.get(0);

						for (Map.Entry<String, String> entry : sr.entrySet()) {

							answersS.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
									.setResizable(true).setSortable(true)
									.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
											& isNumeric(y.get(entry.getKey()).toString())
													? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
															Float.parseFloat(y.get(entry.getKey()).toString()))
													: x.get(entry.getKey()).toString()
															.compareTo(y.get(entry.getKey()).toString()));

							expected.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
									.setResizable(true).setSortable(true)
									.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
											& isNumeric(y.get(entry.getKey()).toString())
													? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
															Float.parseFloat(y.get(entry.getKey()).toString()))
													: x.get(entry.getKey()).toString()
															.compareTo(y.get(entry.getKey()).toString()));
							unexpected.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey())
									.setAutoWidth(true).setResizable(true).setSortable(true)
									.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
											& isNumeric(y.get(entry.getKey()).toString())
													? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
															Float.parseFloat(y.get(entry.getKey()).toString()))
													: x.get(entry.getKey()).toString()
															.compareTo(y.get(entry.getKey()).toString()));

							TextField tf = new TextField();
							tf.setWidth("100%");
							tf.setValue(entry.getValue());
							textfields.put(entry.getKey(), tf);

						}
					} else {

						show_notification("Successful!", "No answer found");
					}

				}
			}

		});

		removeexpected.addClickListener(event -> {

			if (expected.getSelectedItems().size() == 0) {
				show_notification("Warning","Please select at least one element");
			} else {

				rowsE.removeAll(expected.getSelectedItems());
				expected.setItems(rowsE);

			}

		});

		removeunexpected.addClickListener(event -> {

			if (unexpected.getSelectedItems().size() == 0) {
				show_notification("Warning","Please select at least one element");
			} else {

				rowsU.removeAll(unexpected.getSelectedItems());
				unexpected.setItems(rowsU);

			}

		});

		addunexpected.addClickListener(event -> {

			if (answersS.getSelectedItems().size() == 0) {
				show_notification("Warning","Please select at least one element");
			} else {

				rowsU.addAll(answersS.getSelectedItems());
				unexpected.setItems(rowsU);

			}

		});

		addexpected.addClickListener(event -> {

			Dialog d = new Dialog();
			d.setWidth("100%");

			VerticalLayout vl = new VerticalLayout();
			vl.setWidth("100%");
			vl.setHeight("100%");
			for (String tf : textfields.keySet()) {
				HorizontalLayout hl = new HorizontalLayout();
				hl.setWidth("100%");
				hl.setHeight("100%");
				hl.add(new Span(tf));
				hl.add(textfields.get(tf));
				vl.add(hl);
			}

			Button add = new Button("Add");
			vl.add(add);
			d.add(vl);

			add.addClickListener(event2 -> {
				HashMap<String, String> ex = new HashMap<String, String>();

				for (Entry<String, TextField> tf : textfields.entrySet()) {
					ex.put(tf.getKey(), tf.getValue().getValue());
					rowsE.add(ex);
				}
				expected.setItems(rowsE);
				d.close();
			});

			d.open();

		});

		find.addClickListener(event -> {

			lanswersP.setVisible(true);

			/*String t = "use_module(library(semweb/rdf11))";
			org.jpl7.Query q = new org.jpl7.Query(t);
			System.out.println((q.hasSolution() ? "RDF loaded" : ""));
			q.close();*/
			
			String tfd = "use_module(library(clpfd))";
			org.jpl7.Query qfd = new org.jpl7.Query(tfd);
			System.out.println((qfd.hasSolution() ? "CLPFD loaded" : ""));
			qfd.close();
			
			String tr = "use_module(library(clpr))";
			org.jpl7.Query qr = new org.jpl7.Query(tr);
			System.out.println((qr.hasSolution() ? "CLPR loaded" : ""));
			qr.close();

			/*String prule = "";
			for (List<String> r : rules) {
				prule = r.get(0) + ":-";
				for (int i = 1; i < r.size(); i++) {
					prule = prule + r.get(i) + ',';
				}
				prule = prule.substring(0, prule.length() - 1);
				String aprule = "asserta((" + prule + "))";
				org.jpl7.Query q2 = new org.jpl7.Query(aprule);
				System.out.println((q2.hasSolution() ? aprule : ""));
				q2.close();

			}*/
			
			org.jpl7.Query qp = new org.jpl7.Query("['dbpr.pl']");
			System.out.println((qp.hasSolution() ? "Goal success" : ""));	
			qp.close();
 
			
			org.jpl7.Query qq = new org.jpl7.Query("debdb(p,[p('http://dbpedia.org/resource/Italy')],[],Query,Constraints)");
			System.out.println((qq.hasSolution() ? "Goal success" : ""));
			Map<String, org.jpl7.Term> sol = qq.oneSolution();
			qq.close();
			
			
            rowsP.clear();
            
            HashMap<String, org.jpl7.Term> el = new HashMap<String, org.jpl7.Term>();
			 
            for (String a : sol.keySet()) {
				
				
				el.put(a, sol.get(a));	
				
				
			}
				
            rowsP.add(el);
            answersP.setItems(rowsP);
				
				HashMap<String, Term> sr = rowsP.get(0);

				for (Map.Entry<String, Term> entry : sr.entrySet()) {
				
				answersP.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
				.setResizable(true).setSortable(true)
				.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
						& isNumeric(y.get(entry.getKey()).toString())
								? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
										Float.parseFloat(y.get(entry.getKey()).toString()))
								: x.get(entry.getKey()).toString()
										.compareTo(y.get(entry.getKey()).toString()));
				}
					
			 
				
				

			/*for (List<String> r : rules) {

				String dr = r.get(0);
				String rule2 = "retractall(" + dr + ")";
				org.jpl7.Query q4 = new org.jpl7.Query(rule2);
				System.out.println((q4.hasSolution() ? rule2 : ""));
				q4.close();

			}*/

		});

		debug.addClickListener(new ComponentEventListener<ClickEvent<Button>>() {

			@Override
			public void onComponentEvent(ClickEvent<Button> event) {

				debug.setVisible(false);
				addexpected.setVisible(true);
				addunexpected.setVisible(true);
				lexpected.setVisible(true);
				lunexpected.setVisible(true);
				find.setVisible(true);
				removeexpected.setVisible(true);
				removeunexpected.setVisible(true);
				lanswersP.setVisible(false);

			}

		});

		layout.add(lab);
		layout.add(new Span("Please select an example"));
		layout.add(menuBar);
		edS.add(editorS);
		layout.add(edS);
		layout.add(run);
		layout.add(debug);
		layout.add(addexpected);
		layout.add(addunexpected);
		layout.add(find);
		lanswersS.add(answersS);
		layout.add(lanswersS);
		
		lanswersP.add(answersP);		
		layout.add(lanswersP);
		lexpected.add(new Span("List of Expected Answers"));
		lexpected.add(expected);
		layout.add(lexpected);
		layout.add(removeexpected);
		lunexpected.add(new Span("List of Unexpected Answers"));
		lunexpected.add(unexpected);
		layout.add(lunexpected);
		layout.add(removeunexpected);
		edP.add(editorP);
		layout.add(edP);
		editorS.setLiveAutocompletion(true);
		editorP.setVisible(true);
		add(layout);
		this.setHeight("100%");
		this.setWidth("100%");

	}

	public void show_notification(String type, String message) {
		Notification notification = Notification.show(type + " " + message);
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		notification.setPosition(Notification.Position.MIDDLE);
	}

	public void autocompletion(AceEditor editor) {

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
