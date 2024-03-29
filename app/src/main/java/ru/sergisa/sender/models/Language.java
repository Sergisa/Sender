package ru.sergisa.sender.models;

public enum Language {
    AUTO_DETECT(null),
    DISABLE_HIGHLIGHT("nohighlight"),
    _1C("1c"),
    ABNF("abnf"),
    ACCESS_LOGS("accesslog"),
    ADA("ada"),
    ARM_ASSEMBLER("arm"),
    AVR_ASSEMBLER("avrasm"),
    ACTION_SCRIPT("actionscript"),
    APACHE("apache"),
    APPLE_SCRIPT("applescript"),
    ASCII_DOC("asciidoc"),
    ASPECT_J("aspectj"),
    AUTO_HOTKEY("autohotkey"),
    AUTO_IT("autoit"),
    AXAPTA("axapta"),
    AWK("awk"),
    BASH("bash"),
    SHELL("sh"),
    ZSH("zsh"),
    BASIC("basic"),
    BNF("bnf"),
    BRAINFUCK("brainfuck"),
    C("c"),
    C_SHARP("csharp"),
    C_PLUS_PLUS("cpp"),
    CACHE_OBJECT_SCRIPT("cos"),
    C_MAKE("cmake"),
    COQ("coq"),
    CSP("csp"),
    CSS("css"),
    CAPTAIN_PROTO("capnproto"),
    CLEAN("clean"),
    CLOJURE("clojure"),
    COFFEE_SCRIPT("coffeescript"),
    CRMSH("crmsh"),
    CRYSTAL("crystal"),
    D("d"),
    DNS_ZONE_FILE("dns"),
    DOS("dos"),
    BATCH("bat"),
    DART("dart"),
    DELPHI("delphi"),
    DIFF("diff"),
    DJANGO("django"),
    DOCKER_FILE("dockerfile"),
    DSCONFIG("dsconfig"),
    DTS("dts"),
    DUST("dust"),
    EBNF("ebnf"),
    ELIXIR("elixir"),
    ELM("elm"),
    ERLANG("erlang"),
    EXCEL("excel"),
    F_SHARP("fsharp"),
    FIX("fix"),
    FLIX("flix"),
    FORTRAN("fortran"),
    G_CODE("gcode"),
    GAMS("gams"),
    GAUSS("gauss"),
    GHERKIN("gherkin"),
    GO("go"),
    GOLO("golo"),
    GRADLE("gradle"),
    GROOVY("groovy"),
    HTML("html"),
    XML("xml"),
    HTTP("http"),
    HAML("haml"),
    HANDLEBARS("hbs"),
    HASKELL("hs"),
    HAXE("hx"),
    HY("hy"),
    INI("ini"),
    INFORM7("i7"),
    IRPF90("irpf90"),
    JSON("json"),
    JAVA("java"),
    JAVA_SCRIPT("javascript"),
    LASSO("lasso"),
    LEAF("leaf"),
    LESS("less"),
    LDIF("ldif"),
    LISP("lisp"),
    LIVE_CODE_SERVER("livecodeserver"),
    LIVE_SCRIPT("livescript"),
    LLVM("llvm"),
    LUA("lua"),
    MAKEFILE("makefile"),
    MARKDOWN("md"),
    MATHEMATICA("mma"),
    MATLAB("matlab"),
    MAXIMA("maxima"),
    MAYA_EMBEDDED_LANGUAGE("mel"),
    MERCURY("mercury"),
    MIZAR("mizar"),
    MOJOLICIOUS("mojolicious"),
    MONKEY("monkey"),
    MOONSCRIPT("moonscript"),
    N1QL("n1ql"),
    NSIS("nsis"),
    NGINX("nginx"),
    NIMROD("nimrod"),
    NIX("nix"),
    O_CAML("ocaml"),
    OBJECTIVE_C("objectivec"),
    OPENGL_SHADING_LANGUAGE("glsl"),
    OPEN_SCAD("scad"),
    ORACLE_RULES_LANGUAGE("ruleslanguage"),
    OXYGENE("oxygene"),
    PF("pf"),
    PHP("php"),
    PARSER3("parser3"),
    PERL("perl"),
    PONY("pony"),
    POWER_SHELL("ps"),
    PROCESSING("processing"),
    PROLOG("prolog"),
    PROTOCOL_BUFFERS("protobuf"),
    PUPPET("pp"),
    PYTHON("python"),
    PYTHON_PROFILER_RESULTS("profile"),
    Q("k"),
    QML("qml"),
    R("r"),
    RENDER_MAN_RIB("rib"),
    RENDER_MAN_RSL("rsl"),
    ROBOCONF("roboconf"),
    RUBY("ruby"),
    RUST("rust"),
    SCSS("scss"),
    SQL("sql"),
    STEP_PART_21("p21"),
    SCALA("scala"),
    SCHEME("scheme"),
    SCILAB("sci"),
    SMALI("smali"),
    SMALLTALK("smalltalk"),
    STAN("stan"),
    STATA("stata"),
    STYLUS("stylus"),
    SUB_UNIT("subunit"),
    SWIFT("swift"),
    TEST_ANYTHING_PROTOCOL("tap"),
    TCL("tcl"),
    TEX("tex"),
    THRIFT("thrift"),
    TP("tp"),
    TWIG("twig"),
    TYPE_SCRIPT("typescript"),
    VB_NET("vbnet"),
    VB_SCRIPT("vbscript"),
    VHDL("vhdl"),
    VALA("vala"),
    VERILOG("v"),
    VIM("vim"),
    X86_ASSEMBLY("x86asm"),
    XL("xl"),
    X_QUERY("xq"),
    SIMPLE(""),
    ZEPHIR("zep");

    private final String className;

    Language(String name) {
        this.className = name;
    }

    public String getName() {
        return className;
    }

    public static Language getLang(String name) {
        switch (name) {
            case "language-simple":

                return SIMPLE;

            case "language-javascript":
                return JAVA_SCRIPT;

            case "language-html":
                return HTML;

            case "language-php":
                return PHP;

            case "language-basic":
                return BASIC;

            case "language-java":
                return JAVA;

            case "language-json":
                return JSON;

            case "language-prolog":
                return PROLOG;

            case "language-csharp":
                return C_SHARP;

            case "language-cpp":
                return C_PLUS_PLUS;

            case "language-python":
                return PYTHON;

            case "language-sql":
                return SQL;

            case "language-css":
                return CSS;

            case "language-pascal":
                return DELPHI;

            case "language-actionscrip":
                return C_SHARP;


        }
        return null;
    }

}
