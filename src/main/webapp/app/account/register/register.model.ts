export class Registration1 {
  constructor(public login: string, public email: string, public password: string, public langKey: string) {}
}

export class Registration2 {
  constructor(public firstName: string, public lastName: string, public prefix: string, public phoneNumber: string) {}
}
export class Registration3 {
  constructor(
    public burialMethod: string,
    public clothes: string,
    public placeOfCeremony: string,
    public photo: string,
    public graveInscription: string,
    public spotify: string,
    public guests: string,
    public notInvited: string,
    public obituary: string
  ) {}
}
export class Registration4 {
  constructor(
    public purchasedPlace: boolean,
    public isPurchasedOther: string,
    public flowers: boolean,
    public ifFlowers: string,
    public farwellLetter: string,
    public speech: string,
    public videoSpeech: string,
    public testament: string,
    public accessForRelatives: string,
    public other: string
  ) {}
}

export interface registeration {
  reg1: Registration1;
  reg2: Registration2;
  reg3: Registration3;
  reg4: Registration4;
}

export class registeration1 {
  constructor(
    public login: string,
    public email: string,
    public password: string,
    public langKey: string,
    public firstName: string,
    public lastName: string,
    public prefix: string,
    public phoneNumber: string,
    public burialMethod: string,
    public clothes: string,
    public placeOfCeremony: string,
    public photo: string,
    public graveInscription: string,
    public spotify: string,
    public guests: string,
    public notInvited: string,
    public obituary: string,
    public purchasedPlace: boolean,
    public isPurchasedOther: string,
    public flowers: boolean,
    public ifFlowers: string,
    public farwellLetter: string,
    public speech: string,
    public videoSpeech: string,
    public testament: string,
    public accessForRelatives: string,
    public other: string
  ) {}
}
