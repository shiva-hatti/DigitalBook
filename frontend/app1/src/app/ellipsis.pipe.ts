import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'ellipsis'
})
export class EllipsisPipe implements PipeTransform {

  transform(value: string, ...args: number[]): unknown {
    return value.length > 3 ? value.substr(0, args[0]) + '...' : value;
  }

}
