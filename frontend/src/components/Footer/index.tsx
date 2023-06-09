import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = 'API platform by CoderZ';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'github',
          title: <><GithubOutlined />API platform_project</>,
          href: 'https://github.com/Jonstinz/apiplatform',
          blankTarget: true,
        },
      ]}
    />
  );
};
export default Footer;
