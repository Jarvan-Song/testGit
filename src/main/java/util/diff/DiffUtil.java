package util.diff;

import difflib.Chunk;
import difflib.Delta;
import difflib.DiffUtils;
import difflib.Patch;
import util.diff.support.*;
import util.diff.support.algorithm.Algorithm;
import util.diff.support.spliter.ITextSpliter;
import util.diff.support.spliter.LineSpliter;
import util.diff.support.spliter.SentenceSpliter;
import util.diff.support.spliter.WordSpliter;
import util.string.StringUtil;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by songpanfei on 2018/11/19.
 */
public class DiffUtil {

    public static TextDiffResult diffText(String oldText, String newText, Algorithm algorithm){
        ITextSpliter spliter;
        switch (algorithm){
            case splitOnWord:
                spliter = new WordSpliter();
                break;
            case splitOnLine:
                spliter = new LineSpliter();
                break;
            case splitOnSentence:
                spliter = new SentenceSpliter();
                break;
            default:
                spliter = new WordSpliter();
        }
        String[] argOld = spliter.split(oldText);
        String[] argNew = spliter.split(newText);
        List<String> leftList  = Arrays.asList(argOld);
        List<String> rightList = Arrays.asList(argNew);
        System.out.println(leftList);
        System.out.println(rightList);
        Patch patch = DiffUtils.diff(leftList, rightList);
        List<Delta> deltas=  patch.getDeltas();
        for(Delta delta: deltas){
            Chunk left   = delta.getOriginal();
            Chunk right  = delta.getRevised();
            System.out.println(delta.getType() + "  "+left);
            System.out.println(delta.getType() + "  "+right);
            addExtentTag(left,  delta.getType(), leftList);
            addExtentTag(right, delta.getType(), rightList);
        }
        StringBuilder left  = new StringBuilder();
        StringBuilder right = new StringBuilder();
        for(int i=0;i < leftList.size();i++){
            if(StringUtil.isNotEmpty(leftList.get(i))){
                left.append(leftList.get(i));
            }
        }
        for(int i=0;i < rightList.size();i++){
            if(StringUtil.isNotEmpty(rightList.get(i))){
                right.append(rightList.get(i));
            }
        }
        TextDiffResult textDiffResult = new TextDiffResult();
        textDiffResult.setOldText(left.toString());
        textDiffResult.setNewText(right.toString());
        return textDiffResult;
    }

    public static List<String> addExtentTag(Chunk text, Delta.TYPE type, List<String> rightList){
        if(text.size() > 0){
            StringBuilder rightText = new StringBuilder();
            switch (type){
                case CHANGE:
                    rightText.append("<span class=\"diff_modify\">");
                    break;
                case DELETE:
                    rightText.append("<span class=\"diff_del\">");
                    break;
                case INSERT:
                    rightText.append("<span class=\"diff_add\">");
                    break;
            }
            for(int i = text.getPosition();i < text.getPosition() + text.size();i++){
                rightText.append(rightList.get(i));
                rightList.set(i, "");
            }
            rightText.append("</span>");
            rightList.set(text.getPosition(), rightText.toString());
        }
        return rightList;
    }

    public static void main(String[] args){
        TextDiffResult textDiffResult  = diffText("<p>在中国<span>我是</span></p>fefsfadfsdffsfs<a></a>", "<p>美国嗖嗖嗖</p>weirwjfkdsfjksdfasdf<a>sfds</a>",Algorithm.splitOnWord);
//        TextDiffResult textDiffResult  = diffText("我们以前完全不担心吃糖果有可能会蛀牙。", "我们以前完全不火烧吃糖果有可能会蛀牙", Algorithm.splitOnWord);
        System.out.println(textDiffResult.getOldText());
        System.out.println(textDiffResult.getNewText());
//        String text = "<p>几个特点确定了数据集合的属性和结构，包括：变量的数量类型，不同的统计标准像是标准差和峰度。</p>\n" +
//                "<p>统计学中，数据集通常来自于观察抽样调查的数据整体，然后每一行都对应每一个数据样本的观察结果。数据集可以由算法生成，去测试某种软件。 </p>" +
//                "<svg xmlns:xlink=\\\"http://www.w3.org/1999/xlink\\\" style=\\\"fill-opacity:1; color-rendering:auto; color-interpolation:auto; text-rendering:auto; stroke:black; stroke-linecap:square; stroke-miterlimit:10; shape-rendering:auto; stroke-opacity:1; fill:black; stroke-dasharray:none; font-weight:normal; stroke-width:1; font-family:'Dialog'; font-style:normal; stroke-linejoin:miter; font-size:12px; stroke-dashoffset:0; image-rendering:auto;\\\" width=\\\"6\\\" height=\\\"20\\\" xmlns=\\\"http://www.w3.org/2000/svg\\\" class=\\\"transfer_formula\\\"> \\n   <!--Generated by the Batik Graphics2D SVG Generator--> \\n   <defs id=\\\"genericDefs\\\" /> \\n   <g> \\n    <g style=\\\"text-rendering:geometricPrecision; color-rendering:optimizeQuality; color-interpolation:linearRGB; image-rendering:optimizeQuality;\\\" transform=\\\"scale(20,20) translate(0,0.8438) scale(0.1,0.1)\\\"> \\n     <path style=\\\"stroke:none;\\\" d=\\\"M1.8438 -6.9375 L1.8438 -1.0156 Q1.8438 -0.5938 1.9062 -0.4531 Q1.9688 -0.3125 2.1016 -0.2422 Q2.2344 -0.1719 2.5781 -0.1719 L2.5781 0 L0.375 0 L0.375 -0.1719 Q0.6875 -0.1719 0.7969 -0.2344 Q0.9062 -0.2969 0.9766 -0.4453 Q1.0469 -0.5938 1.0469 -1.0156 L1.0469 -5.0781 Q1.0469 -5.8281 1.0078 -6 Q0.9688 -6.1719 0.8984 -6.2344 Q0.8281 -6.2969 0.7031 -6.2969 Q0.5781 -6.2969 0.375 -6.2188 L0.2969 -6.3906 L1.625 -6.9375 L1.8438 -6.9375 Z\\\" /> \\n    </g> \\n   </g> \\n  </svg>";
//        ITextSpliter wordSpliter = new WordSpliter();
//        System.out.println(Arrays.asList(wordSpliter.split(text)));
//        ITextSpliter lineSpliter = new LineSpliter();
//        System.out.println(Arrays.asList(lineSpliter.split(text)));
//        ITextSpliter sentenceSpliter = new SentenceSpliter();
//        System.out.println(Arrays.asList(sentenceSpliter.split(text)));
    }
}
