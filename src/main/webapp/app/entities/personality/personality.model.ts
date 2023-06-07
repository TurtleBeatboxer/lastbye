import { IProfile } from 'app/entities/profile/profile.model';

export interface IPersonality {
  id: number;
  relativeEmail?: string | null;
  profile?: Pick<IProfile, 'id'> | null;
}

export type NewPersonality = Omit<IPersonality, 'id'> & { id: null };
