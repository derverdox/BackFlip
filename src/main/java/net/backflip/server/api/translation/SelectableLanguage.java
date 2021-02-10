package net.backflip.server.api.translation;

public enum  SelectableLanguage {
    // Nur als Idee
    // Nicht dass die einen dann englisch der nächste schreibt ENGLISCH oder sowas

    ENGLISH("english","en-EN"),
    GERMAN("german","de-DE"),
    ;

    private String name;
    private String shortened;

    SelectableLanguage(String name, String shortened){
        this.name = name;
        this.shortened = shortened;
    }

}
