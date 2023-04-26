export interface Article {
  id?: number;
  title?: string;
  content?: string;
  category?: number;
  generatedTime?: Date;
  likes?: number;
  // photos?: Photo[];
}
