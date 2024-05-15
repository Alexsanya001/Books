package my.books.models;


public enum Genre {

    NOVEL("Роман"),
    DETECTIVE("Детектив"),
    SCIENCE("Наука"),
    EDUCATION("Образование"),
    ADVENTURES("Приключения"),
    CHILDREN("Детские"),
    RELIGIOUS("Религия");

    final String translate;

    public String getTranslate() {
        return translate;
   }

   Genre(String translate) {
        this.translate = translate;
    }
}
