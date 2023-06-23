export class profileForm1 {
  constructor(
    private firstName: string,
    private lastName: string,
    private dateOfBirth: string,
    private prefix: number,
    private phoneNumber: number
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
