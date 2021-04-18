package com.pig.easy.bpm.api.core.flowable;

import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.image.impl.DefaultProcessDiagramCanvas;

import java.awt.*;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.awt.font.TextLayout;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * todo:
 *
 * @author : pig
 */
public class CustomProcessDiagramCanvas extends DefaultProcessDiagramCanvas{

    public CustomProcessDiagramCanvas(int width, int height, int minX, int minY, String imageType, String activityFontName, String labelFontName, String annotationFontName, ClassLoader customClassLoader) {
        super(width, height, minX, minY, imageType, activityFontName, labelFontName, annotationFontName, customClassLoader);
        HIGHLIGHT_COLOR = Color.green;
        LABEL_COLOR = Color.black;
    }

    @Override
    public void drawUserTask(String name, GraphicInfo graphicInfo, double scaleFactor) {
        super.drawUserTask(name, graphicInfo, scaleFactor);
    }

    public void drawProcessTextAnnotation(String text, GraphicInfo graphicInfo, double scaleFactor) {
        int x = (int)graphicInfo.getX();
        int y = (int)graphicInfo.getY();
        int width = (int)graphicInfo.getWidth();
        int height = (int)graphicInfo.getHeight();
        Font originalFont = this.g.getFont();
        Stroke originalStroke = this.g.getStroke();
        this.g.setFont(ANNOTATION_FONT);
        Path2D path = new Path2D.Double();
        x = (int)((double)x + 0.5D);
        int lineLength = 26;
        path.moveTo((double)(x + lineLength), (double)y);
        path.lineTo((double)x, (double)y);
        path.lineTo((double)x, (double)(y + height));
        path.lineTo((double)(x + lineLength), (double)(y + height));
        path.lineTo((double)(x + lineLength), (double)(y + height - 1));
        path.lineTo((double)(x + 1), (double)(y + height - 1));
        path.lineTo((double)(x + 1), (double)(y + 1));
        path.lineTo((double)(x + lineLength), (double)(y + 1));
        path.closePath();
        this.g.draw(path);
        int boxWidth = width - 14;
        int boxHeight = height - 14;
        int boxX = x + width / 2 - boxWidth / 2;
        int boxY = y + height / 2 - boxHeight / 2;
        if(scaleFactor == 1.0D && text != null && !text.isEmpty()) {
            drawMultilineAnnotationText1(text, boxX, boxY, boxWidth, boxHeight);
        }
        this.g.setFont(originalFont);
        this.g.setStroke(originalStroke);
    }

    protected void drawMultilineAnnotationText1(String text, int x, int y, int boxWidth, int boxHeight) {
        drawMultilineText1(text, x, y, boxWidth, boxHeight, false);
    }


    protected void drawMultilineText1(String text, int x, int y, int boxWidth, int boxHeight, boolean centered) {

        AttributedString attributedString = new AttributedString(text);
        Font originalFont =  new Font("simsun", Font.PLAIN, 16);	//新建一个 Font 对象
        attributedString.addAttribute(TextAttribute.FONT, originalFont);
        attributedString.addAttribute(TextAttribute.FOREGROUND, Color.BLACK);

        AttributedCharacterIterator characterIterator = attributedString.getIterator();

        int currentHeight = 0;
        java.util.List<TextLayout> layouts = new ArrayList();
        String lastLine = null;
        LineBreakMeasurer measurer = new LineBreakMeasurer(characterIterator, this.g.getFontRenderContext());

        int currentY;
        int height;
        for(TextLayout layout = null; measurer.getPosition() < characterIterator.getEndIndex() && currentHeight <= boxHeight; currentHeight += height) {
            currentY = measurer.getPosition();
            layout = measurer.nextLayout((float)boxWidth);
            height = Float.valueOf(layout.getDescent() + layout.getAscent() + layout.getLeading()).intValue();
            if(currentHeight + height > boxHeight) {
                if(!layouts.isEmpty()) {
                    layouts.remove(layouts.size() - 1);
                    if(lastLine.length() >= 4) {
                        lastLine = lastLine.substring(0, lastLine.length() - 4) + "...";
                    }

                    layouts.add(new TextLayout(lastLine, this.g.getFont(), this.g.getFontRenderContext()));
                } else {
                    layouts.add(layout);
                    currentHeight += height;
                }
                break;
            }

            layouts.add(layout);
            lastLine = text.substring(currentY, measurer.getPosition());
        }

        currentY = y + (centered?(boxHeight - currentHeight) / 2:0);
        TextLayout textLayout;
        for(Iterator var16 = layouts.iterator(); var16.hasNext(); currentY = (int)((float)currentY + textLayout.getDescent() + textLayout.getLeading())) {
            textLayout = (TextLayout)var16.next();
            currentY = (int)((float)currentY + textLayout.getAscent());
            height = x + (centered?(boxWidth - Double.valueOf(textLayout.getBounds().getWidth()).intValue()) / 2:0);
            textLayout.draw(this.g, (float)height, (float)currentY);
        }

    }

    public  void drawCurrentTaskHighLight1(int x, int y, int width, int height) {
        Paint originalPaint = this.g.getPaint();
        Stroke originalStroke = this.g.getStroke();
        this.g.setPaint(Color.red);
        this.g.setStroke(THICK_TASK_BORDER_STROKE);
        RoundRectangle2D rect = new RoundRectangle2D.Double((double)x, (double)y, (double)width, (double)height, 20.0D, 20.0D);
        this.g.draw(rect);
        this.g.setPaint(originalPaint);
        this.g.setStroke(originalStroke);
    }

    public  void drawDisabledTaskHighLight(int x, int y, int width, int height) {
        Paint originalPaint = this.g.getPaint();
        Stroke originalStroke = this.g.getStroke();
        this.g.setPaint(Color.WHITE);
        this.g.setStroke(THICK_TASK_BORDER_STROKE);
        RoundRectangle2D rect = new RoundRectangle2D.Double((double)x, (double)y, (double)width, (double)height, 20.0D, 20.0D);
        this.g.draw(rect);
        this.g.setPaint(originalPaint);
        this.g.setStroke(originalStroke);
    }

    @Override
    public void initialize(String imageType) {
        super.initialize(imageType);

        LABEL_FONT = new Font(labelFontName, Font.BOLD, 11);

    }
}
