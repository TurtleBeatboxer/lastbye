export interface Zero {
  name: string;
  surname: string;
  prefix: number;
  phoneNumber: number;
}

export interface One {
  burialType: number;
  otherBurial: string;
  cremationType: number;
  burialPlace: string;
  ifGraveInscription: boolean;
  openCoffin: boolean;
  graveInscription: string;
  clothes: string;
}

export interface Two {
  ifFlowers: number;
  flowers: string;
  placeOfCeremony: string;
  musicType: number;
  bandName: string;
  ifGuests: number;
  guests: string;
  notInvited: string;
}

export interface Three {
  ifFarewellLetter: number;
  farewellReader: string;
  ifVideoSpeech: number;
  ifTestament: number;
  ifOther4: number;
  other: string;
}

export interface relative {
  name: string;
  email: string;
  phone: string;
}

export interface form {
  zero: Zero;
  one: One;
  two: Two;
  three: Three;
  relative: relative[];
}
