import {HateoasLink} from "./hateoas-link";

export class HateoasObject {
  properties: Map<string, string> = new Map<string, string>();
  links: Map<string, HateoasLink> = new Map<string, HateoasLink>();
  linkedObjects: Map<string, HateoasObject> = new Map<string, HateoasObject>();

  static create(raw: object): HateoasObject {
    let result = new HateoasObject();
    for (let entry of Object.entries(raw)) {
      if (entry[0] == "_links" || entry[0] == "links") {
        let linkEntries = Object.entries(entry[1] as object);
        for (let linkEntry of linkEntries) {
          result.links.set(linkEntry[0], linkEntry[1] as HateoasLink);
        }
        continue;
      }
      result.properties.set(entry[0], entry[1] as string);
    }
    return result;
  }

  getSummary(): string {
    return Array.from(this.properties.values()).join(", ");
  }
}
