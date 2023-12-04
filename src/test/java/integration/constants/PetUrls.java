package integration.constants;

public enum PetUrls {
    MONKEY("https://ichef.bbci.co.uk/news/624/amz/worldservice/live/assets/images/2014/10/16/141016131609_superanimals_ape_624x351_thinkstock.jpg"),
    CAT("https://www.proplan.ru/sites/default/files/styles/image_1920x767/public/breeders/2021-08/1920%25D1%2585767-min.jpg?itok=9imvx4d7"),
    RAT("https://i.pinimg.com/736x/cc/13/41/cc13419817357114c8a8a2d8ae9333ff.jpg"),
    DOG("https://w7.pngwing.com/pngs/438/430/png-transparent-creative-dog-dog-s-cartoon-dog-cartoon-puppy-pet-cute-dog.png");

    private String value;

    PetUrls(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
