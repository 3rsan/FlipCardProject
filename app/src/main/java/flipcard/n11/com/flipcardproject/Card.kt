package flipcard.n11.com.flipcardproject

class Card {
    var name: String? = null
    var image: Int? = null
    var visible: Boolean=false
    var id: Int?=0


    constructor(name: String, image: Int, id: Int) {
        this.name = name
        this.image = image
        this.id=id
    }
}