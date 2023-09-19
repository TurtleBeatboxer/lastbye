export class Account {
  constructor(
    public login: string,
    public levelOfForm: number,

    public langKey: string,
    public prefix: string,
    public phone: number,
    public burialMethod: string,
    public clothes: string,
    public placeOfCeremony: string,

    public graveInscription: string,
    public spotify: string,
    public guests: string,
    public notInvited: string,
    public obituary: string,
    public editsLeft: number,
    public flowers: boolean,
    public ifFlowers: string,
    public farewellLetter: string,
    public openCoffin: boolean,
    public videoSpeech: string,
    public testament: string,

    public other: string,

    public activated: boolean,
    public userId: number,
    public authorities: string[],
    public email: string,
    public firstName: string,

    public lastName: string,
    public imageUrl: string,
    public farewellReader: string,
    public burialPlace: string,
    public musicType: string,
    public burialType: string,
    public ifGraveInscription: boolean,
    public ifPhotoGrave: boolean
  ) {}
}
