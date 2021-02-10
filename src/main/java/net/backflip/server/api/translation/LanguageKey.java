package net.backflip.server.api.translation;

public class LanguageKey {

    private final SelectableLanguage selectableLanguage;
    private final int id;

    public LanguageKey(SelectableLanguage selectableLanguage, int id){
        this.selectableLanguage = selectableLanguage;
        this.id = id;
    }

}
