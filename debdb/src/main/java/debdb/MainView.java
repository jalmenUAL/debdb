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
import org.apache.jena.sparql.core.Var;
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

@Route("debdb")
public class MainView extends VerticalLayout {

	List<List<String>> rules;

	public MainView() {

		VaadinSession.getCurrent().setErrorHandler(new CustomErrorHandler());
		final VerticalLayout layout = new VerticalLayout();
		layout.getStyle().set("width", "100%");
		layout.getStyle().set("background", "#F8F8F8");
		Image lab = new Image("img/banner.png", "banner");
		lab.setWidth("100%");
		lab.setHeight("200px");

		
		Span time = new Span();
		
		Button run = new Button("Run");
		run.setWidth("100%");
		run.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		Button debug = new Button("Debug");
		debug.setWidth("100%");
		debug.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		debug.setVisible(false);
		Button addexpected = new Button("Add Expected Answer");
		addexpected.setWidth("100%");
		addexpected.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		addexpected.setVisible(false);
		Button addunexpected = new Button("Add Unexpected Answer");
		addunexpected.setWidth("100%");
		addunexpected.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
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
				+ "PREFIX xsd: <http://www.w3.org/2001/XMLSchema#>"
				+ "PREFIX dc:      <http://purl.org/dc/elements/1.1/>\n"
				+ "PREFIX dct:     <http://purl.org/dc/terms/>\n"
				+ "PREFIX geo:     <http://www.w3.org/2003/01/geo/wgs84_pos#>\n"
				+ "PREFIX foaf:    <http://xmlns.com/foaf/0.1/>";
		
		
		String exampleA="SELECT ?vehicle WHERE {\n"
				+ "  ?vehicle a dbo:Automobile .\n"
				+ "  ?vehicle dbo:length ?length .\n"
				+ "  ?vehicle dbo:manufacturer	dbr:SEAT\n"
				+ "  FILTER(?length > 4)\n"
				+ "}";

		String exampleB = "SELECT ?movie WHERE {\n"
				+ "  ?movie a dbo:Film .\n"
				+ "  ?movie dbo:runtime ?runtime .\n"
				+ "  ?movie dbo:director dbr:Christopher_Nolan .\n"
				+ "  FILTER (?runtime > 120) \n"
				+ "}";

		String exampleC = "SELECT ?book WHERE {\n"
				+ "  ?book a dbo:Book .\n"
				+ "  ?book dbo:author dbr:J._K._Rowling .\n"
				+ "  ?book dbo:numberOfPages ?pages .\n"
				+ "  FILTER (?pages > 300)\n"
				+ "}";

		String exampleD = "SELECT ?country WHERE {\n"
				+ "  ?country a dbo:Country .\n"
				+ "  ?country dbo:officialLanguage dbr:Spanish_language .\n"
				+ "  ?country dbo:populationTotal ?population .\n"
				+ "  FILTER (?population > 1000000) \n"
				+ "}";

		String exampleE = "SELECT ?company WHERE {\n"
				+ "  ?company a dbo:Company .\n"
				+ "  ?company dbo:industry dbr:Information_technology .\n"
				+ "  ?company dbo:foundingYear ?year .\n"
				+ "  ?company dbo:product	dbr:Software .\n"
				+ "  ?company dbo:numberOfEmployees ?employee .\n"
				+ "  FILTER(?year > 2000) \n"
				+ "  FILTER(?employee < 1000)\n"
				+ "}";

		String exampleF = "SELECT ?river WHERE {\n"
				+ "  ?river a dbo:River .\n"
				+ "  ?river dbo:length ?length .\n"
				+ "  ?river geo:lat ?lat\n"
				+ "  FILTER(?length > 100000) .\n"
				+ "  FILTER(?lat <= 90)\n"
				+ "}";
		
		String exampleG = "SELECT ?mountain WHERE {\n"
				+ "  ?mountain a dbo:Mountain .\n"
				+ "  ?mountain dbo:elevation ?elevation .\n"
				+ "  ?mountain dbo:locatedInArea	dbr:Peru\n"
				+ "  FILTER(?elevation > 5000)\n"
				+ "}";

		String exampleH = "SELECT ?band WHERE {\n"
				+ "  ?band a dbo:Band .\n"
				+ "  ?band  dbo:hometown dbr:Liverpool .\n"
				+ "  ?band dbo:genre dbr:Jazz_music\n"
				+ "}";

		String exampleI = "SELECT ?university WHERE {\n"
				+ "  ?university a dbo:University .\n"
				+ "  ?university dbo:country	dbr:Germany .\n"
				+ "  ?university dbo:numberOfStudents ?students .\n"
				+ "  FILTER(?students < 500)\n"
				+ "}";

		String exampleJ = "SELECT ?museum WHERE {\n"
				+ "  ?museum a dbo:Museum .\n"
				+ "  ?museum dbo:location dbr:Paris .\n"
				+ "  ?museum dbo:numberOfVisitors ?visitors .\n"
				+ "  ?museum geo:lat ?lat\n"
				+ "  FILTER(?lat > 50)\n"
				+ "  FILTER(?visitors < 5000)\n"
				+ "}";
		
		String exampleK = "SELECT ?company WHERE {\n"
				+ "  ?company a dbo:Company .   \n"
				+ "  ?company dbo:locationCountry dbr:United_States .\n"
				+ "  ?company dbo:numberOfEmployees ?employees .   \n"
				+ "  ?company dbo:revenue ?revenue .   \n"
				+ "  ?company dbo:foundingYear ?foundingYear .  \n"
				+ "  FILTER (?employees > 10000)\n"
				+ "  FILTER(?revenue > 100000000)\n"
				+ "  FILTER(?foundingYear < 2000)  \n"
				+ "}";
		
		String exampleL = "SELECT ?planet WHERE {\n"
				+ "  ?planet a dbo:Planet .\n"
				+ "  ?planet dbo:mass ?mass .     \n"
				+ "  ?planet dbo:meanRadius ?radius .   \n"
				+ "  FILTER (?mass > 1E24)          \n"
				+ "  FILTER (?radius < 100000000)   \n"
				+ " \n"
				+ "}";
		
		

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
		else if (e.getSource().getText().equals("Example J")) {

			editorS.setValue(exampleJ);

		}
			
		else if (e.getSource().getText().equals("Example K")) {

			editorS.setValue(exampleK);

		} else if (e.getSource().getText().equals("Example L")) {

			editorS.setValue(exampleL);

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
		basicSubMenu.addItem("Example J", listener);
		basicSubMenu.addItem("Example K", listener);
		basicSubMenu.addItem("Example L", listener);

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
					rowsE.clear();
					rowsU.clear();
					
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
						
						textfields.clear();

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
						
						textfields.clear();
						for ( Var entry: query.getProjectVars()) {
						
							expected.addColumn(h -> h.get(entry.getName())).setHeader(entry.getName()).setAutoWidth(true)
							.setResizable(true).setSortable(true)
							.setComparator((x, y) -> isNumeric(x.get(entry.getName()).toString())
									& isNumeric(y.get(entry.getName()).toString())
											? Float.compare(Float.parseFloat(x.get(entry.getName()).toString()),
													Float.parseFloat(y.get(entry.getName()).toString()))
											: x.get(entry.getName()).toString()
													.compareTo(y.get(entry.getName()).toString()));
							
						
						TextField tf = new TextField();
						tf.setWidth("100%");
						tf.setValue("");
						textfields.put(entry.getName(), tf);
						
						}
						show_notification("Successful!", "No answer found.");
					}

				}
			}

		});

		removeexpected.addClickListener(event -> {
			
			

			if (expected.getSelectedItems().size() == 0) {
				show_notification("Warning:", "Please select at least one element");
			} else {

				lanswersP.setVisible(false);
				rowsE.removeAll(expected.getSelectedItems());
				expected.setItems(rowsE);

			}

		});

		removeunexpected.addClickListener(event -> {
			
			

			if (unexpected.getSelectedItems().size() == 0) {
				show_notification("Warning:", "Please select at least one element");
			} else {

				lanswersP.setVisible(false);
				rowsU.removeAll(unexpected.getSelectedItems());
				unexpected.setItems(rowsU);

			}

		});

		addunexpected.addClickListener(event -> {
			
			

			if (answersS.getSelectedItems().size() == 0) {
				show_notification("Warning:", "Please select at least one element");
			} else {

				lanswersP.setVisible(false);
				rowsU.addAll(answersS.getSelectedItems());
				unexpected.setItems(rowsU);

			}

		});

		addexpected.addClickListener(event -> {
			
			lanswersP.setVisible(false);

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
					
				}
				
				rowsE.add(ex);
				expected.setItems(rowsE);
				d.close();
			});

			d.open();

		});

		find.addClickListener(event -> {

			lanswersP.setVisible(true);
			rowsP.clear();
		    
			org.jpl7.Query qp = new org.jpl7.Query("['dbprex.pl']");
			System.out.println((qp.hasSolution() ? "Goal success" : ""));
			qp.close();
			
			

			
			String prule = "";
			for (List<String> r : rules) {
				prule = r.get(0) + ":-";
				for (int i = 1; i < r.size(); i++) {
					prule = prule + r.get(i) + ',';
				}
				prule = prule.substring(0, prule.length() - 1);
				System.out.println(prule);
				String aprule = "asserta((" + prule + "))";
				org.jpl7.Query qar = new org.jpl7.Query(aprule);
				System.out.println((qar.hasSolution() ? aprule : ""));
				qar.close();

			}
			 

			
			String exp = "[";
			
			for (HashMap<String, String> r: rowsE) {
				
			
				String sexpected = "p(";
				
				 
				for (Entry<String, String> key: r.entrySet())
			     {
					if (key.getValue().contains("^^"))
					{   
						 
						String[] parts = key.getValue().split("\\^\\^");
						
						sexpected = sexpected + ","+ parts[0]+"^^'"+parts[1]+"'";
					}
					else  sexpected = sexpected + ",'" + key.getValue()+"'";
			     }
				sexpected = (sexpected + ")").replaceFirst(",","");
				exp = exp + "," + sexpected;
				
			}
			exp = (exp + "]").replaceFirst(",","");
			 
			
			String unexp = "[";
			
			for (HashMap<String, String> r: rowsU) {
				
			
				String sunexpected = "p(";
				
				 
				for (Entry<String, String> key: r.entrySet())
			     {
					if (key.getValue().contains("^^"))
					{   
						 
						String[] parts = key.getValue().split("\\^\\^");
						
						sunexpected = sunexpected + ","+ parts[0]+"^^'"+parts[1]+"'";
					}
					else  sunexpected = sunexpected + ",'" + key.getValue()+"'";
			     }
				sunexpected = (sunexpected + ")").replaceFirst(",","");
				unexp = unexp + "," + sunexpected;
				
			}
			unexp = (unexp + "]").replaceFirst(",","");
			 
			 
			        long startTime = System.nanoTime();

			org.jpl7.Query qq = new org.jpl7.Query(
				"debdb(p,"+exp+","+unexp+",Query,Constraints,Replacements)");
			
			System.out.println("debdb(p,"+exp+","+unexp+",Query,Constraints,Replacements)");

			long endTime = System.nanoTime();
			
			long duration = (endTime - startTime)/1000;  
			
			time.setText("Debugging result in "+String.valueOf(duration)+ "ms");
			
			System.out.println((qq.hasSolution() ? "Goal success" : ""));
			
			rowsP.clear();
			
			if (qq.hasSolution()) {
			
			Map<String, org.jpl7.Term> sol = qq.getSolution();
			
			HashMap<String, org.jpl7.Term> el = new HashMap<String, org.jpl7.Term>();

			for (String a : sol.keySet()) {

				el.put(a, sol.get(a));

			}
			
			rowsP.add(el);
			
			if (qq.hasNext())
			{
			
			Map<String, org.jpl7.Term> sol2 = qq.nextSolution();
			
			HashMap<String, org.jpl7.Term> el2 = new HashMap<String, org.jpl7.Term>();

			for (String a : sol2.keySet()) {

				el2.put(a, sol2.get(a));

			}
			
			rowsP.add(el2);
			
			if (qq.hasNext())
				
			{
			
			Map<String, org.jpl7.Term> sol3 = qq.nextSolution();
			
			HashMap<String, org.jpl7.Term> el3 = new HashMap<String, org.jpl7.Term>();

			for (String a : sol3.keySet()) {

				el3.put(a, sol3.get(a));

			}
			
			rowsP.add(el3);
			
			if (qq.hasNext())
				
			{
			
			Map<String, org.jpl7.Term> sol4 = qq.nextSolution();
			
			HashMap<String, org.jpl7.Term> el4 = new HashMap<String, org.jpl7.Term>();

			for (String a : sol4.keySet()) {

				el4.put(a, sol4.get(a));

			}
			
			if (qq.hasNext())
				
			{
			
			rowsP.add(el4);
			
			Map<String, org.jpl7.Term> sol5 = qq.nextSolution();
			
			HashMap<String, org.jpl7.Term> el5 = new HashMap<String, org.jpl7.Term>();

			for (String a : sol5.keySet()) {

				el5.put(a, sol5.get(a));

			}
			
			rowsP.add(el5);
			
			}
			}
			}
			}
			
			
			
			qq.close();
			answersP.removeAllColumns();
			answersP.setItems(rowsP);
			
			 
			answersP.getElement().setProperty("white-space", "normal");
			answersP.getElement().setProperty("word-wrap", "break-word");
			answersP.getElement().setProperty("overflow-wrap", "anywhere");
			answersP.getElement().setProperty("font-size", "7pt");
			
			HashMap<String, Term> sr = rowsP.get(0);

			for (Map.Entry<String, Term> entry : sr.entrySet()) {

				answersP.addColumn(h -> h.get(entry.getKey())).setHeader(entry.getKey()).setAutoWidth(true)
						.setResizable(true).setSortable(true)
						.setComparator((x, y) -> isNumeric(x.get(entry.getKey()).toString())
								& isNumeric(y.get(entry.getKey()).toString())
										? Float.compare(Float.parseFloat(x.get(entry.getKey()).toString()),
												Float.parseFloat(y.get(entry.getKey()).toString()))
										: x.get(entry.getKey()).toString().compareTo(y.get(entry.getKey()).toString()));
			}

			} else {show_notification("Debugging unsuccessfull:","There is not query for your request"); lanswersP.setVisible(false);}
			
			for (List<String> r : rules) {

				String dr = r.get(0);
				String rule = "retractall(" + dr + ")";
				org.jpl7.Query qrr = new org.jpl7.Query(rule);
				System.out.println((qrr.hasSolution() ? rule : ""));
				qrr.close();

			}
			 

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
		lanswersS.add(new Span("Answers"));
		lanswersS.add(answersS);
		layout.add(lanswersS);
		lanswersP.add(time); 
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
