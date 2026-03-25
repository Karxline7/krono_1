import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SolicitudesUser } from './solicitudes-user';

describe('SolicitudesUser', () => {
  let component: SolicitudesUser;
  let fixture: ComponentFixture<SolicitudesUser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SolicitudesUser],
    }).compileComponents();

    fixture = TestBed.createComponent(SolicitudesUser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
