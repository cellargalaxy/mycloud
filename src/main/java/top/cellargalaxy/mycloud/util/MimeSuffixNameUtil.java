package top.cellargalaxy.mycloud.util;

import eu.medsea.mimeutil.MimeUtil;

import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cellargalaxy
 * @time 2018/11/6
 */
public class MimeSuffixNameUtil {
    private static final Map<String, String> SUFFIX_NAME_2_MIME = new HashMap<>();
    private static final Map<String, String> MIME_2_SUFFIX_NAME = new HashMap<>();

    static {
        SUFFIX_NAME_2_MIME.put("apk", "application/vnd.android.package-archive");
        SUFFIX_NAME_2_MIME.put("3gp", "video/3gpp");
        SUFFIX_NAME_2_MIME.put("ai", "application/postscript");
        SUFFIX_NAME_2_MIME.put("aif", "audio/x-aiff");
        SUFFIX_NAME_2_MIME.put("aifc", "audio/x-aiff");
        SUFFIX_NAME_2_MIME.put("aiff", "audio/x-aiff");
        SUFFIX_NAME_2_MIME.put("asc", "text/plain");
        SUFFIX_NAME_2_MIME.put("atom", "application/atom+xml");
        SUFFIX_NAME_2_MIME.put("au", "audio/basic");
        SUFFIX_NAME_2_MIME.put("avi", "video/x-msvideo");
        SUFFIX_NAME_2_MIME.put("bcpio", "application/x-bcpio");
        SUFFIX_NAME_2_MIME.put("bin", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("bmp", "image/bmp");
        SUFFIX_NAME_2_MIME.put("cdf", "application/x-netcdf");
        SUFFIX_NAME_2_MIME.put("cgm", "image/cgm");
        SUFFIX_NAME_2_MIME.put("class", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("cpio", "application/x-cpio");
        SUFFIX_NAME_2_MIME.put("cpt", "application/mac-compactpro");
        SUFFIX_NAME_2_MIME.put("csh", "application/x-csh");
        SUFFIX_NAME_2_MIME.put("css", "text/css");
        SUFFIX_NAME_2_MIME.put("dcr", "application/x-director");
        SUFFIX_NAME_2_MIME.put("dif", "video/x-dv");
        SUFFIX_NAME_2_MIME.put("dir", "application/x-director");
        SUFFIX_NAME_2_MIME.put("djv", "image/vnd.djvu");
        SUFFIX_NAME_2_MIME.put("djvu", "image/vnd.djvu");
        SUFFIX_NAME_2_MIME.put("dll", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("dmg", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("dms", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("doc", "application/msword");
        SUFFIX_NAME_2_MIME.put("dtd", "application/xml-dtd");
        SUFFIX_NAME_2_MIME.put("dv", "video/x-dv");
        SUFFIX_NAME_2_MIME.put("dvi", "application/x-dvi");
        SUFFIX_NAME_2_MIME.put("dxr", "application/x-director");
        SUFFIX_NAME_2_MIME.put("eps", "application/postscript");
        SUFFIX_NAME_2_MIME.put("etx", "text/x-setext");
        SUFFIX_NAME_2_MIME.put("exe", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("ez", "application/andrew-inset");
        SUFFIX_NAME_2_MIME.put("flv", "video/x-flv");
        SUFFIX_NAME_2_MIME.put("gif", "image/gif");
        SUFFIX_NAME_2_MIME.put("gram", "application/srgs");
        SUFFIX_NAME_2_MIME.put("grxml", "application/srgs+xml");
        SUFFIX_NAME_2_MIME.put("gtar", "application/x-gtar");
        SUFFIX_NAME_2_MIME.put("gz", "application/x-gzip");
        SUFFIX_NAME_2_MIME.put("hdf", "application/x-hdf");
        SUFFIX_NAME_2_MIME.put("hqx", "application/mac-binhex40");
        SUFFIX_NAME_2_MIME.put("htm", "text/html");
        SUFFIX_NAME_2_MIME.put("html", "text/html");
        SUFFIX_NAME_2_MIME.put("ice", "x-conference/x-cooltalk");
        SUFFIX_NAME_2_MIME.put("ico", "image/x-icon");
        SUFFIX_NAME_2_MIME.put("ics", "text/calendar");
        SUFFIX_NAME_2_MIME.put("ief", "image/ief");
        SUFFIX_NAME_2_MIME.put("ifb", "text/calendar");
        SUFFIX_NAME_2_MIME.put("iges", "model/iges");
        SUFFIX_NAME_2_MIME.put("igs", "model/iges");
        SUFFIX_NAME_2_MIME.put("jnlp", "application/x-java-jnlp-file");
        SUFFIX_NAME_2_MIME.put("jp2", "image/jp2");
        SUFFIX_NAME_2_MIME.put("jpe", "image/jpeg");
        SUFFIX_NAME_2_MIME.put("jpeg", "image/jpeg");
        SUFFIX_NAME_2_MIME.put("jpg", "image/jpeg");
        SUFFIX_NAME_2_MIME.put("js", "application/x-javascript");
        SUFFIX_NAME_2_MIME.put("kar", "audio/midi");
        SUFFIX_NAME_2_MIME.put("latex", "application/x-latex");
        SUFFIX_NAME_2_MIME.put("lha", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("lzh", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("m3u", "audio/x-mpegurl");
        SUFFIX_NAME_2_MIME.put("m4a", "audio/mp4a-latm");
        SUFFIX_NAME_2_MIME.put("m4p", "audio/mp4a-latm");
        SUFFIX_NAME_2_MIME.put("m4u", "video/vnd.mpegurl");
        SUFFIX_NAME_2_MIME.put("m4v", "video/x-m4v");
        SUFFIX_NAME_2_MIME.put("mac", "image/x-macpaint");
        SUFFIX_NAME_2_MIME.put("man", "application/x-troff-man");
        SUFFIX_NAME_2_MIME.put("mathml", "application/mathml+xml");
        SUFFIX_NAME_2_MIME.put("me", "application/x-troff-me");
        SUFFIX_NAME_2_MIME.put("mesh", "model/mesh");
        SUFFIX_NAME_2_MIME.put("mid", "audio/midi");
        SUFFIX_NAME_2_MIME.put("midi", "audio/midi");
        SUFFIX_NAME_2_MIME.put("mif", "application/vnd.mif");
        SUFFIX_NAME_2_MIME.put("mov", "video/quicktime");
        SUFFIX_NAME_2_MIME.put("movie", "video/x-sgi-movie");
        SUFFIX_NAME_2_MIME.put("mp2", "audio/mpeg");
        SUFFIX_NAME_2_MIME.put("mp3", "audio/mpeg");
        SUFFIX_NAME_2_MIME.put("mp4", "video/mp4");
        SUFFIX_NAME_2_MIME.put("mpe", "video/mpeg");
        SUFFIX_NAME_2_MIME.put("mpeg", "video/mpeg");
        SUFFIX_NAME_2_MIME.put("mpg", "video/mpeg");
        SUFFIX_NAME_2_MIME.put("mpga", "audio/mpeg");
        SUFFIX_NAME_2_MIME.put("ms", "application/x-troff-ms");
        SUFFIX_NAME_2_MIME.put("msh", "model/mesh");
        SUFFIX_NAME_2_MIME.put("mxu", "video/vnd.mpegurl");
        SUFFIX_NAME_2_MIME.put("nc", "application/x-netcdf");
        SUFFIX_NAME_2_MIME.put("oda", "application/oda");
        SUFFIX_NAME_2_MIME.put("ogg", "application/ogg");
        SUFFIX_NAME_2_MIME.put("ogv", "video/ogv");
        SUFFIX_NAME_2_MIME.put("pbm", "image/x-portable-bitmap");
        SUFFIX_NAME_2_MIME.put("pct", "image/pict");
        SUFFIX_NAME_2_MIME.put("pdb", "chemical/x-pdb");
        SUFFIX_NAME_2_MIME.put("pdf", "application/pdf");
        SUFFIX_NAME_2_MIME.put("pgm", "image/x-portable-graymap");
        SUFFIX_NAME_2_MIME.put("pgn", "application/x-chess-pgn");
        SUFFIX_NAME_2_MIME.put("pic", "image/pict");
        SUFFIX_NAME_2_MIME.put("pict", "image/pict");
        SUFFIX_NAME_2_MIME.put("png", "image/png");
        SUFFIX_NAME_2_MIME.put("pnm", "image/x-portable-anymap");
        SUFFIX_NAME_2_MIME.put("pnt", "image/x-macpaint");
        SUFFIX_NAME_2_MIME.put("pntg", "image/x-macpaint");
        SUFFIX_NAME_2_MIME.put("ppm", "image/x-portable-pixmap");
        SUFFIX_NAME_2_MIME.put("ppt", "application/vnd.ms-powerpoint");
        SUFFIX_NAME_2_MIME.put("ps", "application/postscript");
        SUFFIX_NAME_2_MIME.put("qt", "video/quicktime");
        SUFFIX_NAME_2_MIME.put("qti", "image/x-quicktime");
        SUFFIX_NAME_2_MIME.put("qtif", "image/x-quicktime");
        SUFFIX_NAME_2_MIME.put("ra", "audio/x-pn-realaudio");
        SUFFIX_NAME_2_MIME.put("ram", "audio/x-pn-realaudio");
        SUFFIX_NAME_2_MIME.put("ras", "image/x-cmu-raster");
        SUFFIX_NAME_2_MIME.put("rdf", "application/rdf+xml");
        SUFFIX_NAME_2_MIME.put("rgb", "image/x-rgb");
        SUFFIX_NAME_2_MIME.put("rm", "application/vnd.rn-realmedia");
        SUFFIX_NAME_2_MIME.put("roff", "application/x-troff");
        SUFFIX_NAME_2_MIME.put("rtf", "text/rtf");
        SUFFIX_NAME_2_MIME.put("rtx", "text/richtext");
        SUFFIX_NAME_2_MIME.put("sgm", "text/sgml");
        SUFFIX_NAME_2_MIME.put("sgml", "text/sgml");
        SUFFIX_NAME_2_MIME.put("sh", "application/x-sh");
        SUFFIX_NAME_2_MIME.put("shar", "application/x-shar");
        SUFFIX_NAME_2_MIME.put("silo", "model/mesh");
        SUFFIX_NAME_2_MIME.put("sit", "application/x-stuffit");
        SUFFIX_NAME_2_MIME.put("skd", "application/x-koan");
        SUFFIX_NAME_2_MIME.put("skm", "application/x-koan");
        SUFFIX_NAME_2_MIME.put("skp", "application/x-koan");
        SUFFIX_NAME_2_MIME.put("skt", "application/x-koan");
        SUFFIX_NAME_2_MIME.put("smi", "application/smil");
        SUFFIX_NAME_2_MIME.put("smil", "application/smil");
        SUFFIX_NAME_2_MIME.put("snd", "audio/basic");
        SUFFIX_NAME_2_MIME.put("so", "application/octet-stream");
        SUFFIX_NAME_2_MIME.put("spl", "application/x-futuresplash");
        SUFFIX_NAME_2_MIME.put("src", "application/x-wais-source");
        SUFFIX_NAME_2_MIME.put("sv4cpio", "application/x-sv4cpio");
        SUFFIX_NAME_2_MIME.put("sv4crc", "application/x-sv4crc");
        SUFFIX_NAME_2_MIME.put("svg", "image/svg+xml");
        SUFFIX_NAME_2_MIME.put("swf", "application/x-shockwave-flash");
        SUFFIX_NAME_2_MIME.put("t", "application/x-troff");
        SUFFIX_NAME_2_MIME.put("tar", "application/x-tar");
        SUFFIX_NAME_2_MIME.put("tcl", "application/x-tcl");
        SUFFIX_NAME_2_MIME.put("tex", "application/x-tex");
        SUFFIX_NAME_2_MIME.put("texi", "application/x-texinfo");
        SUFFIX_NAME_2_MIME.put("texinfo", "application/x-texinfo");
        SUFFIX_NAME_2_MIME.put("tif", "image/tiff");
        SUFFIX_NAME_2_MIME.put("tiff", "image/tiff");
        SUFFIX_NAME_2_MIME.put("tr", "application/x-troff");
        SUFFIX_NAME_2_MIME.put("tsv", "text/tab-separated-values");
        SUFFIX_NAME_2_MIME.put("txt", "text/plain");
        SUFFIX_NAME_2_MIME.put("ustar", "application/x-ustar");
        SUFFIX_NAME_2_MIME.put("vcd", "application/x-cdlink");
        SUFFIX_NAME_2_MIME.put("vrml", "model/vrml");
        SUFFIX_NAME_2_MIME.put("vxml", "application/voicexml+xml");
        SUFFIX_NAME_2_MIME.put("wav", "audio/x-wav");
        SUFFIX_NAME_2_MIME.put("wbmp", "image/vnd.wap.wbmp");
        SUFFIX_NAME_2_MIME.put("wbxml", "application/vnd.wap.wbxml");
        SUFFIX_NAME_2_MIME.put("webm", "video/webm");
        SUFFIX_NAME_2_MIME.put("wml", "text/vnd.wap.wml");
        SUFFIX_NAME_2_MIME.put("wmlc", "application/vnd.wap.wmlc");
        SUFFIX_NAME_2_MIME.put("wmls", "text/vnd.wap.wmlscript");
        SUFFIX_NAME_2_MIME.put("wmlsc", "application/vnd.wap.wmlscriptc");
        SUFFIX_NAME_2_MIME.put("wmv", "video/x-ms-wmv");
        SUFFIX_NAME_2_MIME.put("wrl", "model/vrml");
        SUFFIX_NAME_2_MIME.put("xbm", "image/x-xbitmap");
        SUFFIX_NAME_2_MIME.put("xht", "application/xhtml+xml");
        SUFFIX_NAME_2_MIME.put("xhtml", "application/xhtml+xml");
        SUFFIX_NAME_2_MIME.put("xls", "application/vnd.ms-excel");
        SUFFIX_NAME_2_MIME.put("xml", "application/xml");
        SUFFIX_NAME_2_MIME.put("xpm", "image/x-xpixmap");
        SUFFIX_NAME_2_MIME.put("xsl", "application/xml");
        SUFFIX_NAME_2_MIME.put("xslt", "application/xslt+xml");
        SUFFIX_NAME_2_MIME.put("xul", "application/vnd.mozilla.xul+xml");
        SUFFIX_NAME_2_MIME.put("xwd", "image/x-xwindowdump");
        SUFFIX_NAME_2_MIME.put("xyz", "chemical/x-xyz");
        SUFFIX_NAME_2_MIME.put("zip", "application/zip");

        SUFFIX_NAME_2_MIME.entrySet().stream().forEach(entry -> MIME_2_SUFFIX_NAME.put(entry.getValue(), entry.getKey()));
    }

    static {
        MimeUtil.registerMimeDetector("eu.medsea.mimeutil.detector.MagicMimeMimeDetector");
    }

    public static final String suffixName2Mime(String suffixName) {
        return SUFFIX_NAME_2_MIME.get(suffixName);
    }

    public static final String mime2SuffixName(String mime) {
        return MIME_2_SUFFIX_NAME.get(mime);
    }

    public static final String getMimeType(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        Collection mimeType = MimeUtil.getMimeTypes(inputStream);
        return mimeType != null ? mimeType.toString() : null;
    }
}
