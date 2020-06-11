/**
 * Copyright (C) 2009 - 2013 SC 4ViewSoft SRL
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package GuiForm;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.SeriesSelection;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Form;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.geom.Shape;
import Entity.Formation;
import Services.ServiceFormation;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import java.util.Random;





/**
 * Budget demo pie chart.
 */
public class FormationPieChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
      
    return "Formation chart";
  }
  
    public int getRandomColor(){
   Random rnd = new Random();
   return ColorUtil.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
}
    
      protected DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(50);
        renderer.setLegendTextSize(70);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }
@Override
    protected CategorySeries buildCategoryDataset(String title, double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
     DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(50);
    renderer.setLegendTextSize(70);
         renderer.setChartTitleTextSize(50);
    for (Formation c : ServiceFormation.getInstance().getAllFormations()) {
  
      series.add( c.getNom(), c.getNbrplace());
      
    
    }
    return series;
  }
  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "Le nombre de places disponibles par formation";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
      double[] values = new double[30];
      int[] colors = new int[30]; 
        Integer i=0;
        for (Formation c : ServiceFormation.getInstance().getAllFormations()) {
            values[i] = (double)c.getNbrplace();
            i++;     
        }
        Integer j=0;
     for (Formation c : ServiceFormation.getInstance().getAllFormations()) {
            colors[j] = getRandomColor();
            j++;     
        }
        
    final DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextFont(largeFont);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    renderer.setBackgroundColor(ColorUtil.rgb(243, 242, 242));
    renderer.setApplyBackgroundColor(true);
    renderer.setLabelsColor(0xE30304);
    
  
      Style s = UIManager.getInstance().getComponentStyle("TitleCommand");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_HOME, s);
    
    final CategorySeries seriesSet = buildCategoryDataset("Project budget", values);
    final PieChart chart = new PieChart(seriesSet, renderer);
    ChartComponent comp = new ChartComponent(chart);
    return wrap("Budget", comp);
    

}}
