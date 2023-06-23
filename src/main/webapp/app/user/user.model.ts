export class User {
  constructor(
    public login: string,
    public phone: number,
    public prefix: string,
    public burialMethod: string,
    public clothes: string,
    public placeOfCeremony: string,
    public photo: string,
    public graveInscription: string,
    public spotify: string,
    public guest: string,
    public notInvited: string,
    public obituary: string,
    public purchasedPlace: boolean,
    public isPurchasedOther: string,
    public flowers: boolean,
    public ifFlowers: string,
    public farewellLetter: string,
    public speech: string,
    public videoSpeech: string,
    public testament: string,
    public accessesForRelatives: string,
    public other: string,
    public levelOfForm: number,
    public isOpenCoffin: string
  ) {}
}
