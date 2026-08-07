# Library-System-OOP
 
Project developed for the **Programação com Objectos (PO)** course, 2025/2026, Instituto Superior Técnico.
 
## Description
 
A Java application to manage a library's collection. It supports searching works, registering users and works, and managing loan requests.
 
## Main concepts
 
- **Creators** — authors/directors, identified by name, linked to their works.
- **Works** — books (authors, ISBN) or DVDs (director, IGAC number), each with a title, price, category, and copies in stock.
- **Users** — patrons with a name and email, either *active* or *suspended* (for overdue loans/unpaid fines), and classified as *faltoso*, *cumpridor*, or *normal* based on their return history, which affects loan limits and periods.
- **Loan requests** — validated against an ordered set of rules (duplicates, suspension, stock, limits, category, price); late returns trigger suspension and daily fines.
- **Notifications** — users can be notified when a work is borrowed or becomes available again.
Application state can be saved/loaded via Java serialization, and an initial dataset can be imported from a text file at startup.
 
## User interaction
 
The interface has a **Main Menu** (open/save state, show/advance date, and access to sub-menus) and three sub-menus:
 
- **User Management** — register user, show user(s), show user notifications, pay fine.
- **Work Management** — show work(s), show works by creator, change work inventory, perform search.
- **Request Management** — request work, return work.
## Importing data
 
An initial dataset can be loaded from a text file (`import` property), one entity per line:
 
```
DVD:title:director:price:category:IGACNumber:copies
BOOK:title:authors:price:category:ISBN:copies
USER:name:email
```
 
## Project structure
 
| Folder | Contents |
|---|---|
| `app/` | Application layer — menus, commands, prompts and messages (`bci.app.*`). |
| `core/` | Domain layer — the library's business logic: creators, works, users, requests, rules, notifications, persistence (`bci.core`). |
| `po-uilib/` | Generic, reusable menu/command and form/display support library used by `app/` (`pt.tecnico.uilib`). |
| `uml/` | UML diagrams from the initial design phase of the application. |
 
## Build and run
 
The project is built and run with the provided `Makefile`:
 
```bash
make run
```
 
This compiles the sources in `app/`, `core/` and `po-uilib/` and launches the application (`bci.app.App`).
 
## Author
 
- [Margarida Guedes](https://github.com/margaridamguedes)
