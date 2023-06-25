export class profileForm1 {
  constructor(
    public firstName: string,
    public lastName: string,
    public dateOfBirth: string,
    public prefix: number,
    public phoneNumber: number
  ) {}
}

export class profileForm2 {
  constructor(public burialMethod: string, public graveInscription: string, public openCoffin: boolean, public clothes: string) {}
}

export class profileData2 {
  constructor(
    public burialMethod: string,
    public graveInscription: string,
    public openCoffin: boolean,
    public clothes: string,
    public userId: number,
    public login: string,
    public levelOfForm: number
  ) {}
}

export class profileForm3 {
  constructor(
    public flowers: boolean,
    public ifFlowers: string,
    public obituary: string,
    public spotify: string,
    public guests: string,
    public notInvited: string,
    public placeOfCeremony: string
  ) {}
}

export class profileData3 {
  constructor(
    public flowers: boolean,
    public ifFlowers: string,
    public obituary: string,
    public spotify: string,
    public guests: string,
    public notInvited: string,
    public placeOfCeremony: string,
    public userId: number,
    public login: string,
    public levelOfForm: number
  ) {}
}

export class profileForm4 {
  constructor(public farewellLetter: string, public videoSpeech: string, public testament: string, public other: string) {}
}

export class profileData4 {
  constructor(
    public farewellLetter: string,
    public videoSpeech: string,
    public testament: string,
    public other: string,
    public userId: number,
    public login: string,
    public levelOfForm: number
  ) {}
}
