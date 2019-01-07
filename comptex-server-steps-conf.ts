'use strict';

import * as _ from 'lodash';
import * as actions from './actions';
import * as acl from './acl';
import * as utils from '../utils';
import profiles from './conf_profiles';

const channels = {
    codeChannel: {
        name: "J'ai un code",
    },
    supannMailPerso: {
        name: "Courriel",
    },
    pager: {
        name: "SMS",
    },
    email2gest: {
        name: "Assistance DSI",
    },
    email2gestetu: {
        name: "Assistance étudiants",
    },
};

const account_identification = {
    profilename: {
        choices: [
            { key: "student", name: "Etudiant", sub : { supannEtuId: {} } },
            { key: "staff", name: "Personnel", sub : { supannEmpId: {} } },
            { key: "other", name: "Autre", sub : { supannAliasLogin: {} } },
        ],
    },
    birthDay: {},
};

const personalAttrs = (_v) => ({
    sn: {},
    givenName: {},
    homePostalAddress: {},
    // TODO
});

const userPasswordAttrs = (v) => ({
    uid: { hidden: true },
    ...personalAttrs(v),
});

const initialSteps: steps = {
    activation: {
        attrs: account_identification,
        labels: {
            title: 'Activation de votre compte',
            description: 'Veuillez saisir les champs suivants qui vont vous permettre de poursuivre la procédure :',
            okButton: "Confirmer",            
        },
        nextBrowserStep: 'activation_',
    },
    activation_: {
        action_pre: actions.esup_activ_bo_validateAccount,
        attrs: (v) => ({
            charter: {},
            ...userPasswordAttrs(v),
        }),
        labels: {
            title: 'Activation de votre compte',
            description: 'xxx {{v_pre}} {{v_pre.profilename}}',
            okButton: "Activation",
        },
        action_post: actions.createCompte,
    },
    lost_password: {
        labels: {},
        attrs: account_identification,
        nextBrowserStep: 'lost_password_channel',
    },
    lost_password_channel: {
        action_pre: actions.chain([
            actions.esup_activ_bo_validateAccount, // get ldap attrs (including uid) + "possibleChannels"
            actions.anonymize_personal_info,
        ]),
        attrs: (v: v) => ({ // NB: param "v" is server-side "v", so it contains all attrs returned by "action_pre" 
            uid: {},
            channel: { 
                choices: _.map(v['possibleChannels'] as string[], (key) => ({ ...channels[key], key })),
            },
            // attrs needed for labels.description (this step and nextBrowserStep)
            // NB: value is anonymized by actions.anonymize_personal_info
            supannMailPerso: { readonly: true, uiHidden: 'hidden' }, 
            pager: { readonly: true, uiHidden: 'hidden' }, 
        } as StepAttrsOption),
        labels: {
            description: `Votre courriel alternatif est de la forme {{v.supannMailPerso}} <p>
            Votre numéro de portable est de la forme {{v.pager}} <p>
            Ne choisissez Assistance DSIUN que si vous n'avez pas renseigné de courriel alternatif ou de numéro portable ou si ces informations sont erronées. <p>
            Veuillez choisir le mode d'envoi du code qui vous permettra de réinitialiser votre mot de passe : `,
            okButton: 'Confirmer',
        },
        action_post: actions.esup_activ_bo_sendCode,
        nextBrowserStep: 'lost_password_code',
    },
    lost_password_code: {
        labels: { 
            description: `
            <div v-if="v_pre.channel === 'code'">
            Vous avez choisi de passer directement à l'étape de saisie du code. Si vous ne disposez pas de code valide, veuillez reprendre la procédure et choisir un autre moyen pour recevoir votre code de confirmation.
            </div>
            <div v-if="v_pre.channel === 'supannMailPerso'">
            <p>Un courrier électronique a été envoyé à l'adresse <b>{{v_pre.supannMailPerso}}</b>.</p>
            <p>Ce courrier fournit le code nécessaire pour réinitialiser votre mot de passe.</p>
            <p>Un certain temps peut être nécessaire avant la réception des messages. N'oubliez pas de vérifier que l'adresse électronique ci-dessus est correcte et que le message n'est pas passé dans votre dossier de messages indésirables.</p>
            </div>
            <p>
            Veuillez entrer le code de validation qui vous a été fourni :`,
        },
        attrs: { uid: {}, code: {} },
        nextBrowserStep: 'lost_password_',
    },
    lost_password_: {
        action_pre: actions.chain([
            actions.esup_activ_bo_validateCode,
            actions.esup_activ_bo_validateAccount, // get it again, non anonymized
        ]),
        attrs: userPasswordAttrs,
        labels: {},
        action_post: actions.createCompte,
    },
};
const nextSteps = {};

export const steps = utils.mergeSteps(initialSteps, nextSteps);
