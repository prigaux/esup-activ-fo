Routes
-------------------------------------------------------

* `src/main/webapp/stylesheets/welcome.jsp`\
  propose les items "listBeanProcedureWithoutCas" ou "listBeanProcedureWithCas", et "beanFieldStatus" (controllers.xml)
* `src/main/webapp/stylesheets/accountAuthentification.jsp`\
   Demande login & mot de passe. Appelle ensuite xfire "authentificateUserWithCas" ou "authentificateUser" (avec proxy ticket ou mot de passe)
* `src/main/webapp/stylesheets/accountIdentification.jsp`\
  demande les beanFields de controllers.xml "beanFieldStatus" pour le status choisi. Appelle ensuite xfire "validateAccount"
* `src/main/webapp/stylesheets/accountChoice.jsp`\
  choix du mode d'envoi du code de réinitialisation de mot de passe. channels autorisés envoyés par esup-activ-bo. Appelle ensuite xfire "sendCode"
* `src/main/webapp/stylesheets/accountCode.jsp`\
  "Veuillez entrer le code de validation qui vous a été fourni". Appelle ensuite xfire "validateCode"
* `src/main/webapp/stylesheets/accountPersonalInfo.jsp`\
  gère l'affichage/interaction des différents beanfields (`categoryBeanDataChangeDigest`). "Vous pouvez modifier ici vos informations personnelles :". Appelle ensuite xfire "updatePersonalInformations" et envoie des mails
* `src/main/webapp/stylesheets/accountCharter.jsp`\
  page d'acceptation de la charte. Pas d'action
* `src/main/webapp/stylesheets/accountPassword.jsp`\
  Demande "Nouveau Mot de passe". Appelle ensuite xfire "setPassword"
* `src/main/webapp/stylesheets/accountLogin.jsp`\
  page de chgt de login (non utilisé). Appelle ensuite xfire "changeLogin"
* `src/main/webapp/stylesheets/accountEnabled.jsp`\
  page de fin (activation, réinit/chgt de mot de passe, chgt de login)
* `src/main/resources/properties/jsf/navigation-rules.xml`


"BeanField"s
--------------------------------------------------------------
* `src/main/resources/properties/web/`
  * `fieldLogin.xml`
  * `fieldPassword.xml`
  * `fieldCode.xml`
  * `fieldNewLogin.xml`
  * `fieldNewPassword.xml`
  * `status.xml`
  * `process.xml`
  * `infoToValidate.xml`
  * `infoPerso.xml`
* `src/main/java/org/esupportail/activfo/web/beans/BeanField.java`
* `src/main/java/org/esupportail/activfo/web/beans/BeanFieldImpl.java` \
  `disable` veut dire non-éditable (`<input disabled>`)
* `src/main/webapp/stylesheets/accountDataView.jsp`\
  gère l'affichage des différents beanfields. Inutilisé à Paris1
* `src/main/webapp/stylesheets/accountDataChange.jsp`\
  gère l'affichage/interaction des différents beanfields (`categoryBeanDataChange`). Appelle ensuite xfire "updatePersonalInformations" et envoie des mails

* `src/main/webapp/stylesheets/_includeAccountData.jsp`\
  gère le sous-menu des catégories à gauche
* `src/main/resources/properties/web/infoDataView.xml`\
  déclare des CategoryBeanFieldImpl + 3 BeanFieldImpl link/linkPerso. Inutilisé à Paris1
* `src/main/resources/properties/web/infoDataChange.xml`\
  déclare des CategoryBeanFieldImpl
* `src/main/java/org/esupportail/activfo/web/beans/CategoryBeanFieldImpl.java`\
  vérifie "profile", possède "title" et beanfields "profile" vérifiant les conditions dans "beanFieldProfile" et "deniedBeanFieldProfile" (NB: *substring* filter on account values by attribute)


Channel
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/domain/beans/channels/`
  * `ChannelMailPerso.java`\
    remplace les 4 premiers caractères de l'adresse mail par "****"
  * `ChannelPager.java`\
    remplace les 6 premiers caractères du pager (normalisé sur 10 chiffres sans espace) par "****"
  * `src/main/resources/properties/web/channels.xml`\
    liste de beans avec "Channel"/name/label/homeMsg/codeMsg. Utilisé comme "channels" dans controllers.xml


Converter
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/web/converters/`
  * `PostalAddressConverter.java`\
    remplace les "$" par " "
  * `AnotherStudentConverter.java`\
    ajouter préfix {UAI:"+etablissement":ETUID} pour supannEtuID ???
  * `MapOrEqualConverter.java`\
    utilisé pour eduPersonPrimaryAffiliation (le nom du bean "mapOrEqualConverter" devrait être modifié pour mentionner eduPersonAffiliation)
  * `MapOrDefaultConverter.java`\
    utilisé pour eduPersonAffiliation (le nom du bean "mapOrDefaultConverter" devrait être modifié pour mentionner eduPersonAffiliation)
  * `StringConverter.java`\
    à supprimer, duplique MapOrEqualConverter
  * `RedirectMailConverter.java`\
    utilisé pour mailForwardingAddress en lien avec supannMailPerso. Prend la valeur supannMailPerso si mailForwardingAddress ne commence pas par "\\" ???
  * `MailForwardingAddressConverter.java`\
    utilisé pour mailForwardingAddress en lien avec supannMailPerso. Renvoie mailForwardingAddress sauf si egal à un des supannMailPerso (qui est mono-valué, bizarre)
  * `NewLoginConverter.java`\
    génère un login à partir du displayName (conversion en ascii, première lettre de chaque mot + dernier mot)
  * `LdapStructuresConverter.java`\
    transforme un supannCodeEntite en "description" pour affichage
  * `AttributesReplaceConverter.java`\
    INUTILISÉ   renvoie la chaîne en remplaçant les '{attribute}' par leur valeur associée au compte de l'utilisateur courant (utile par exemple pour {uid} dans une url) 
  * `LdapPhoneFaxConverter.java`\
    stocke au format international, affiche si possible au format français
  * `EmptyAndFormatPhoneNumberConverter.java`\
    stocke au format international, affiche si possible au format français. Utilise la valeur magique JSF DEFAULT_VALUE
  * `LdapDateConverter.java`\
    stocke au format date voulu, affiche au format date voulu
  * `LdapActiviteConverter.java`\
    transforme supannActivite (up1tableKey) en displayName pour affichage
  * `LdapTablesConverter.java`\
    transforme supannRoleGenerique (up1tableKey) en displayName, gère la transformation texte en code
* `src/main/resources/properties/web/converters.xml`\
  liste de beans de type "Converter"


Validator
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/web/validators/`
  * `ValidatorCode.java`\
    vérifie que la valeur est un nombre de 8 chiffres
  * `ValidatorStudentLogin.java`\
    vérifie que la valeur est un nombre de 11 chiffres
  * `ValidatorHarpegeNumber.java`\
    vérifie que la valeur est un nombre ou une chaine vide
  * `ValidatorPassword.java`\
    vérifie que les caractères sont isAsciiPrintable
  * `ValidatorBirthDate.java`\
    vérifie que la valeur est au bon format de date
  * `ValidatorValueMatches.java`\
    regex validation
  * `ValidatorList.java`\
    Validation via une succession de validateur. Si un convertisseur est disponible, c'est la valeur convertie qui est passée aux validateurs
  * `ValidatorBeanFieldOrINotEmpty.java`\
    vérifie qu'un des beans est rempli
  * `ValidatorPhone.java`\
    non utilisé
  * `ValidatorCheckboxLinkedInput.java`\
    Validateur d'un checkbox lié à un input (passé via beanField). Lance ValidatorException si le checkbox est coché et que l'Input est vide. 
  * `ValidatorCheckboxSMSAgreementLinkedInput.java`\
    Validateur du checkbox SMSAgreement lié à un input (passé via beanField). Lance ValidatorException si le checkbox est coché (autre que l'autorisation photo, ce dernier n'a pas besoin d'être controlé) et que l'Input est vide.
  * `ValidatorLogin.java`\
    vérifie que le login est valide en fonction du displayName du compte
  * `ValidatorPhoto.java`\
    vérifie que l'extension jpg/jpeg et validation via ImageIO
  * `ValidatorDisplayName.java`\
    vérifie que le displayName est construit à partir de givenName/up1AltGivenName & sn/up1BirthName
* `src/main/resources/properties/web/validators.xml`\
  liste de beans de type "Validator"


messages en français
--------------------------------------------------------------
* `src/main/resources/properties/i18n/bundles/JsfMessages_fr.properties`
* `src/main/resources/properties/i18n/bundles/Custom_fr.properties`
* `src/main/resources/properties/i18n/bundles/Messages_fr.properties`

Javascript
--------------------------------------------------------------
* `src/main/webapp/media/scripts/accountDataTabs.js`
* `src/main/webapp/media/functions.js`
* `src/main/webapp/media/scripts/croppieForm.js`
* `src/main/webapp/media/scripts/form-show-hide.js`

Divers
--------------------------------------------------------------
* `src/main/resources/properties/domain/domain.xml`
* `src/main/java/org/esupportail/activfo/web/beans/SelectItemsList.java`\
  transforme une `Map<String,String>` en `ArrayList<SelectItem>`
* `src/main/java/org/esupportail/activfo/web/renderkit/html/SelectBooleanCheckboxRenderer.java`\
  wrapper de JSF HtmlCheckboxRenderer pour gérer Converter (?)
* `src/main/java/org/esupportail/activfo/web/beans/BeanMultiValueImpl.java`\
  utilisé pour stocké la/les valeur(s) d'un BeanField pour JSF
* `src/main/java/org/esupportail/activfo/web/beans/LdapAttributeItem.java`\
  valeur d'un attribut de l'utilisateur. Utile pour un checkbox/select
* `src/main/java/org/esupportail/activfo/web/beans/ProfileItem.java`\
  isAllowed conditionné à l'age de l'utilisateur
* `src/main/java/org/esupportail/activfo/web/beans/LdapStructureList.java`\
  créé un `<select>` avec `<optgroup>` pour chaque "businessCategoryMap" valeur, les `<option>`s étant les résultats d'une recherche LDAP 
* `src/main/java/org/esupportail/activfo/domain/beans/Account.java`\
  stocke les infos de l'utilisateur, en grande partie venant de esup-activ-bo (cf AccountController.updateCurrentAccount)
* `src/main/java/org/esupportail/activfo/domain/beans/mailing/MailingImpl.java`\
  gère l'envoi de mail avec système de template {xxx} où "xxx" est un attribut du compte 
* `src/main/resources/properties/web/controllers.xml` 
* `src/main/java/org/esupportail/activfo/web/controllers/AccountController.java`\
  gère bcp de choses
* `src/main/resources/properties/config.sample.properties`
* `src/main/webapp/stylesheets/_includeProgessBar.jsp`
* `src/main/webapp/stylesheets/_includeBreadcrumb.jsp`



XFire
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/services/client/CasAccountManagement.java`
* `src/main/java/org/esupportail/activfo/services/client/AccountManagement.java`
* `src/main/resources/properties/client/client.xml`


Rien d'important
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/services/application/VersionningServiceImpl.java`
* `src/main/java/org/esupportail/activfo/services/authentication/ProxyTicketGeneratorImpl.java`
* `src/main/java/org/esupportail/activfo/services/ldap/LdapSchema.java`
* `src/main/java/org/esupportail/activfo/web/WebXml.java`
* `src/main/java/org/esupportail/activfo/domain/beans/User.java`
* `src/main/java/org/esupportail/activfo/services/authentication/AuthenticatorImpl.java`
* `src/main/java/org/esupportail/activfo/domain/DomainService.java`\
  (authentificateUserWithCodeKey INUTILISÉ)
* `src/main/resources/properties/misc/application.xml`
* `src/main/resources/properties/cache/ehcache.xml`
* `src/main/resources/properties/auth/auth.xml`
* `src/main/resources/properties/applicationContext.xml`
* `src/main/resources/properties/smtp/smtp.xml`
* `src/main/resources/properties/misc/abstractBeans.xml`
* `src/main/resources/properties/ldap/ldap.xml`\
   configure esup-commons LDAP
* `src/main/webapp/media/esupActivation.css`


esup-commons, non fonctionnel ou non utilisé :
--------------------------------------------------------------
* `src/main/java/org/esupportail/activfo/web/deepLinking/DeepLinkingRedirectorImpl.java`
* `src/main/resources/properties/deepLinking/deepLinking.xml`
