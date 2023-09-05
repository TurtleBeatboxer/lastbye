import { IProfile } from 'app/entities/profile/profile.model';

export interface IFiles {
  id: number;
  name?: string | null;
  type?: string | null;
  format?: string | null;
  filePath?: string | null;
  profile?: Pick<IProfile, 'id'> | null;
}

export type NewFiles = Omit<IFiles, 'id'> & { id: null };
