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
package statistiques;

import Entity.Articles_especes;
import Services.RefugieService;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Form;

import java.util.Random;





/**
 * Budget demo pie chart.
 */
public class EventPieChart extends AbstractDemoChart {
  /**
   * Returns the chart name.
   * 
   * @return the chart name
   */
  public String getName() {
      
    return "Events chart";
  }
  
    public int getRandomColor(){
   Random rnd = new Random();
   return ColorUtil.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
}
    

    protected CategorySeries buildCategoryDataset(String title, String[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    for (Articles_especes c : RefugieService.getInstance().getAllRefugie()) {
 
      series.add( c.getContenu(), c.getNumlike());
      
    
    }
    return series;
  }
  /**
   * Returns the chart description.
   * 
   * @return the chart description
   */
  public String getDesc() {
    return "The number of like par article";
  }

  /**
   * Executes the chart demo.
   * 
   * @param context the context
   * @return the built intent
   */
  public Form execute() {
      double[] values = new double[5];
      int[] colors = new int[30]; 
        Integer i=0;
        for (Articles_especes c : RefugieService.getInstance().getAllRefugie()) {
            values[i] = (double)c.getNumlike();
            i++;     
        }
        Integer j=0;
        for (Articles_especes c : RefugieService.getInstance().getAllRefugie()) {
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
    renderer.setLabelsColor(0000);
    final CategorySeries seriesSet = buildCategoryDataset("Project Statistiques", values);
    final PieChart chart = new PieChart(seriesSet, renderer);
    ChartComponent comp = new ChartComponent(chart);
    return wrap("Statistiques LIKE", comp);
    

}

   
}
